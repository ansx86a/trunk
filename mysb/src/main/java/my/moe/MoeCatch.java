package my.moe;

import dao.MoePoolMapper;
import dao.domain.MoePool;
import dao.domain.MoePoolExample;
import http.HttpUtils;
import my.共用;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import utils.Utils;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Component
public class MoeCatch {
    private HttpUtils h = new HttpUtils();
    private static List<Integer> skipPost = Arrays.asList(2057, 2924, 4098);
    private static String fileSavePath = "d:/moe/post";

    private MoePoolMapper moePoolMapper;

    public void main() throws Exception {

        用title3重新命名:
        {
            if (false) {
                break 用title3重新命名;
            }
            MoeCatch a = new MoeCatch();
            a.用title3重新命名();
            System.out.println("end 重新命名");
            return;
        }

        網頁的部分:
        {
            // https://yande.re/pool?page=157
            // https://yande.re/pool?page=1
            MoeCatch a = new MoeCatch();
            for (int i = 2; i <= 30; i++) {
                String url = "https://yande.re/pool?page=" + i;
                a.readlist(url);
            }
        }
        System.out.println("end");
    }


    // 讀取列表頁
    public void readlist(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        URI uri = new URI(url);
        Elements es = null;
        es = doc.select("table.highlightable tr a");
        for (Element o : es) {
            String title = o.text();
            String uriPath = o.attr("href");
            URI absUri = uri.resolve(uriPath);
            String postNum = StringUtils.substringAfterLast(uriPath, "/");

            MoePoolExample ex = new MoePoolExample();
            ex.createCriteria()
                    .andPostidEqualTo(Integer.parseInt(postNum))
                    .andTitle1EqualTo(title)
                    .andUrlEqualTo(uriPath)
                    .andAbsurlEqualTo(absUri.toString());
            MoePool mp = new MoePool();
            mp.setPostid(Integer.parseInt(postNum));
            mp.setTitle1(title);
            mp.setUrl(uriPath);
            mp.setAbsurl(absUri.toString());

            List<MoePool> list = moePoolMapper.selectByExample(ex);
            if (list.size() > 0) {
                System.out.println("檔案已存在:" + postNum);
            } else if (skipPost.contains(Integer.parseInt(postNum))) {
                System.out.println("跳過無法下載的zip檔：" + postNum);
            } else {
                readPostPage(absUri, mp);
            }
        }
    }

    // 進入list pool頁
    public void readPostPage(URI uri, MoePool map) throws Exception {
        Document doc = Jsoup.connect(uri.toString()).get();
        Elements es = null;
        String download = doc.select("#subnavbar a:contains(Download)").attr("href");
        URI absDownload = uri.resolve(StringUtils.substringBefore(download, "?"));
        map.setDownload(download);
        map.setAbsdownload(absDownload.toString());
        es = doc.select("#pool-show");
        es = es.get(0).children();// 設成div下面的那一層
        if (es.size() == 3) {// 只有3列的才記錄，2列的不記錄，2列的不包含subtitle
            String subTitle = es.get(1).html().split("\n")[0].trim(); // 本來是用text()，會把換行吃掉，加入會有多餘的資料所以用 換行符號分割subtitle
            if (StringUtils.containsAny(subTitle, "http", "<a")) {// 如果有http或是<a，表示不是真的subtitle而是怪註解，砍掉

            } else if (subTitle.length() <= 6
                    || StringUtils.endsWithAny(subTitle.toLowerCase(), "pireze", "available from", "available from:")) {// 如果長度小於5也不採用
            } else {
                map.setTitle2(subTitle);
            }
        }
        String fileName = map.getTitle2() == null ? map.getTitle1() : map.getTitle2();// 主要用subTitle當檔名
        fileName = 共用.處理檔名(fileName);
        File f = 共用.checkFile(fileSavePath, fileName);
        map.setFilePath(f.toString());

        捉檔案(absDownload.toString(), Utils.getResourceFromRoot("爬蟲/萌妹cookies.txt"), f);
        System.out.println("新增資料:" + map);
        moePoolMapper.insert(map);
    }


    public void 捉檔案(String url, File cookieFile, File zipFile) throws Exception {
        String s = FileUtils.readFileToString(cookieFile);
        h.setCookieStore(s);
        h.cookiesHttpTry(url, zipFile, 10);
    }

    public void 用title3重新命名() {
        List<MoePool> list = moePoolMapper.selectByExample(new MoePoolExample());

        for (MoePool map : list) {
            String filePath = (String) map.getFilePath();
            String title3 = (String) map.getTitle3();
            Integer postid = (Integer) map.getPostid();
            if (postid < 4200) {// 因為有好幾批下載，會有檔名重覆的問題，所以以postid區間來當成重新命名的依據
                continue;
            }

            if (StringUtils.isNotBlank(title3)) {
                title3 = 共用.處理檔名(title3);
                File f = new File(filePath);
                File newFile = 共用.checkFile(fileSavePath, title3);
                if (f.exists() && !newFile.exists()) {// 舊檔存在，新檔不存在
                    f.renameTo(newFile);
                    System.out.println("" + newFile + "--" + f);
                }
            }
        }
    }
}
