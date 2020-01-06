package my.ex;

import dao.ExPoolMapper;
import dao.domain.ExPool;
import dao.domain.ExPoolExample;
import http.HttpUtils;
import my.共用;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Controller
@RequestMapping("ex")
public class ExCatch {
    enum Site {
        e, ex;

        public String toString() {
            if (this == e) {
                return "https://e-hentai.org";
            }
            if (this == ex) {
                return "https://exhentai.org";
            }
            return "";
        }
    }

    enum Extype {
        爬蟲, 捉圖, 檢查不下載,
    }

    private static Site site = Site.ex;
    private Extype type = Extype.捉圖;
    private HttpUtils h = new HttpUtils();
    // 檔案相關
    private String fileSavePath = "d:/moe/ex";
    private File outDir;
    private ExPool row;

    // 同步操作相關
    public static Object obj = new Object();
    private long count = 0;
    private long stopCount = 46000;
    private long 時間間隔 = 2_000;
    public Date d = new Date(System.currentTimeMillis() + 時間間隔);
    public Random rand = new Random();

    public Date stopDate = DateUtils.addMinutes(new Date(), 1 * 60 * 24);// 多久停止
    @Autowired
    private ExPoolMapper exPoolMapper;

    public void main() throws Exception {
        ExCatch ex = this;
        ex.init();
        if (ex.type == Extype.爬蟲) {// 從20頁開始捉，我不想捉到有上傳到一半的
//            for (int i = 6700; i <= 6900; i++) {// 感覺有很多莫名的資料，很古怪，順序有問題？改天全部重掃嗎？
			for (int i = 200; i <= 300; i++) {
                // String url = "https://exhentai.org/?page=" + i;
                //要再確認一次url有沒有變，是不是變成全讀cookies，才不會把不要的東西弄進來
                String url = site == Site.ex ? "https://exhentai.org/?page=" + i : "https://e-hentai.org/?page=" + i + "&f_cats=745";
                System.out.println(url);
                ex.讀取文章列表(url);
                Thread.sleep(5_000);// 每個主頁分開2秒，才不會讀太快
            }
            System.out.println("end");
            return;
        }

        if (ex.type == Extype.捉圖) {
            for (ExPool m : 列表未下載已排序的網扯()) {
                if (檢查是否強制停止()) {
                    return;
                }

                System.out.println(ToStringBuilder.reflectionToString(m));
                String pageUrl = m.getUrl();

                ex.row = m;
                String dir = 共用.處理檔名(m.getTitle1());
                ex.outDir = 共用.checkFile(ex.fileSavePath, dir, "(exid_" + m.getExid() + ")");
                System.out.println(ex.outDir);
                if (site == Site.e) {
                    pageUrl = pageUrl.replaceFirst(Pattern.quote(Site.ex.toString()), Site.e.toString());
                }
                ex.圖檔列表頁面(pageUrl);
                m.setDownloaded(1);
                exPoolMapper.updateByPrimaryKey(m);
            }
        }
        System.out.println("end");
    }

    private List<ExPool> 列表未下載已排序的網扯() {
        ExPoolExample example = new ExPoolExample();
        example.setOrderByClause("exid desc");
        example.createCriteria().andDownloadedEqualTo(0).andLookedEqualTo(1);
        List<ExPool> list = exPoolMapper.selectByExample(example);
        System.out.println("total count:" + list.size());
        return list;
    }

    public void init() throws IOException {
        // 設定可並行的管線數目，設x就程示同時跑x+1個，不用參數的話要研究ForkJoinPool，太麻煩了
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "5");
        String path = site == Site.ex ? "my/ex.txt" : "my/e.txt";
        String cookieStr = FileUtils.readFileToString(Utils.getResourceFromRoot(path));
        h.setCookieStore(cookieStr);
    }

    public void 讀取文章列表(String url) throws IOException {
        String result = h.cookiesHttp(url);// 讀取
        Document doc = Jsoup.parse(result);
        Elements es = doc.select(".gl3c a");
        for (Element e : es) {
            // System.out.println(e.outerHtml());// 這裡可以取得網扯和名字
            String mainPageUrl = e.attr("href");
            if (site == Site.e) {
                mainPageUrl = mainPageUrl.replaceAll(Pattern.quote(Site.e.toString()), Site.ex.toString());
            }

            String exid = mainPageUrl.split("/")[4];
            // String title = e.text();
            String title = e.select(".glink").get(0).text();
            ExPoolExample example = new ExPoolExample();
            example.createCriteria()
                    .andExidEqualTo(Integer.parseInt(exid))
                    .andTitle1EqualTo(title)
                    .andUrlEqualTo(mainPageUrl);
            List<ExPool> list = exPoolMapper.selectByExample(example);
            if (list.size() > 0) {
                System.out.print("------已經存在了");
                System.out.println(ToStringBuilder.reflectionToString(list.get(0)));
                return;
            }
            ExPool exPool = new ExPool();
            exPool.setExid(Integer.parseInt(exid));
            exPool.setDownloaded(0);
            exPool.setLooked(0);
            exPool.setTitle1(title);
            exPool.setUrl(mainPageUrl);
            if (exPoolMapper.selectByPrimaryKey(exPool.getExid()) == null) {
                System.out.print("inserting");
                System.out.println(ToStringBuilder.reflectionToString(exPool));
                exPoolMapper.insert(exPool);
            } else {
                System.out.println("updating");
                System.out.println(ToStringBuilder.reflectionToString(exPool));
                exPoolMapper.updateByPrimaryKey(exPool);
            }
        }
    }

    public void 圖檔列表頁面(String pageUrl) throws Exception {
        if (檢查是否強制停止()) {
            throw new RuntimeException("超過了");
        }

        String result = 多次重試並取得url結果(pageUrl, 20);
        if (StringUtils.isBlank(result)) {
            System.out.println("無法取得列表清單，但是繼續執行不中斷出錯");
            return;
        }

        Document doc = Jsoup.parse(result);
        Elements es = doc.select(".gdtl a");
        es.parallelStream().forEach(e -> {
            String imgUrl = e.attr("href");
            String page = e.text();
            System.out.println("page = " + page + ",imgUrl=" + imgUrl);
            try {
                boolean saveOk = 圖檔頁(imgUrl, page);// 核心的下載程式
            } catch (Exception ex) {
                System.out.println("管線操作發生ex");
                ex.printStackTrace();
            }
        });

        String nextPageUrl = 分析出下一頁網扯(doc);
        if (StringUtils.isNotBlank(nextPageUrl)) {
            圖檔列表頁面(nextPageUrl);// 遞迴
        }
    }

    private String 多次重試並取得url結果(String pageUrl, int times) throws InterruptedException, IOException {
        for (int i = 0; i < times; i++) {
            try {
                return h.cookiesHttp(pageUrl);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Thread.sleep(3000);
        }
        String newLog = outDir.getAbsolutePath() + "listError " + row.getExid() + "_" + new Date().getTime() / 1000 % 3600 + ".txt";
        File newLogFile = new File(newLog);
        String log = "" + row.getUrl() + "\r\n" + pageUrl;
        FileUtils.writeStringToFile(newLogFile, log);
        return "";
    }

    private String 分析出下一頁網扯(Document doc) {
        Elements es0 = doc.select("table.ptt td");
        if (es0.size() > 0) {
            Element e0 = es0.get(es0.size() - 1);
            if (StringUtils.equals(e0.attr("class"), "ptdd")) {
                System.out.println("最後一頁");
            } else {
                return e0.select("a").attr("href");
            }
        }
        return "";
    }


    /**
     * @param imgUrl
     * @param page
     * @return
     * @throws Exception
     */
    public boolean 圖檔頁(String imgUrl, String page) throws Exception {
        String html = 取得圖檔頁資訊(imgUrl, page);
        if (StringUtils.isBlank(html)) {// 捉不到圖檔頁的html，就無法執行，就跳開
            return false;
        }
        Document doc = Jsoup.parse(html);
        String imgSrc = doc.select("#i3 img").attr("src");
        String info = doc.select("#i4").text();
        String fail = doc.select("#loadfail").attr("onclick");
		String failValue = StringUtils.substringBefore(StringUtils.substringAfter(fail, "'"), "'");
        System.out.println(imgSrc);
        System.out.println(info);

        boolean saveFlag = 存圖檔(imgUrl, imgSrc, page, failValue);
        if (!saveFlag) {
            String newLog = outDir.getAbsolutePath() + "imgError_" + StringUtils.substringAfterLast(imgSrc, "/") + "_"
                    + new Date().getTime() / 1000 % 3600 + ".txt";
            File newLogFile = new File(newLog);
            String log = "" + row.getUrl() + "\r\n" + info + "\r\n" + imgSrc + "\r\n" + imgUrl;
            FileUtils.writeStringToFile(newLogFile, log);
        }

        return saveFlag;
    }

    /**
     * 解析出圖檔頁的html來
     *
     * @param imgUrl
     * @param page
     * @return
     * @throws IOException
     */
    public String 取得圖檔頁資訊(String imgUrl, String page) throws IOException {
        String result = null;
        for (int i = 0; i < 10; i++) {
            try {
                doThreadSleep();
                count++;
                return h.cookiesHttp(imgUrl);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        String newLog = outDir.getAbsolutePath() + "pageError_" + page + "_" + new Date().getTime() / 1000 % 3600
                + ".txt";
        File newLogFile = new File(newLog);
        String log = "" + row.getUrl() + "\r\n" + imgUrl;
        FileUtils.writeStringToFile(newLogFile, log);
        return "";
    }

    /**
     * @param imgUrl
     * @param imgSrc
     * @param page
     * @param fail
     * @return
     * @throws Exception
     */
    public boolean 存圖檔(String imgUrl, String imgSrc, String page, String fail)
            throws Exception {
        String newPage = String.format("%05d", Integer.parseInt(page));
        // 存圖檔
        File outFile = 共用.checkFile(outDir.getAbsolutePath(),
                newPage + "_" + StringUtils.substringAfterLast(imgSrc, "/"), "");
        try {
            HttpUtils.httpTry(imgSrc, outFile, 5);
        } catch (Exception ex) {
            if (imgUrl.indexOf("?nl=") < 0) {
                return 圖檔頁(imgUrl + "?nl=" + fail, page);
            }
            return false;
        }
        return true;
    }

    public void doThreadSleep() throws Exception {
        while (true) {
            // System.out.println(Thread.currentThread().getId() + "-" + new
            // Date(System.currentTimeMillis()));
            synchronized (obj) {
                if (System.currentTimeMillis() - d.getTime() > 0) {
                    d = new Date(System.currentTimeMillis() + 時間間隔 - 500 + rand.nextInt(1000));// 弄亂時間差
                    System.out.println("下一次可執行時間" + Thread.currentThread().getId() + "-" + d);
                    break;
                }
            }
            Thread.sleep(300 + rand.nextInt(300));
        }
        System.out.println("執行時間:" + Thread.currentThread().getId() + "-" + new Date(System.currentTimeMillis()));
    }

    private boolean 檢查是否強制停止() {
        if (stopDate.before(new Date())) {
            System.out.println("超過執行時間，強制停止");
            return true;
        }
        if (count > stopCount) {
            System.out.println("stop count=" + count);
            return true;
        }

        return false;
    }
}
