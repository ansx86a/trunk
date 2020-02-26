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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import utils.Utils;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("moe")
public class MoeCatch {
    private HttpUtils h = new HttpUtils();
    private static List<Integer> skipPost = Arrays.asList(2057, 2924, 4098, 5604, 5603, 5602, 5588, 5458, 5436, 5339, 5203, 5121, 5114,
            96957, 96780, 6247, 5618, 5544, 6158, 5750, 96641, 5751, 6101, 6022, 6100, 6009);


    private static String fileSavePath = "d:/moe/post";

    @Autowired
    private MoePoolMapper moePoolMapper;

    @RequestMapping("main")
    public void main() throws Exception {
        用title3重新命名:
        {
            if (true) {
                break 用title3重新命名;
            }
            MoeCatch a = this;
            a.用title3重新命名();
            System.out.println("end 重新命名");
            return;
        }

        網頁的部分:
        {
            // https://yande.re/pool?page=157
            // https://yande.re/pool?page=1
            MoeCatch a = this;
            for (int i = 2; i <= 60; i++) {
                try {
                    String url = "https://yande.re/pool?page=" + i;
                    //System.out.println(url);
                    a.readlist(url);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    continue;
                }
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
    public void readPostPage(URI uri, MoePool moePool) throws Exception {
        Document doc = Jsoup.connect(uri.toString()).get();
        Elements es = null;
        String download = doc.select("#subnavbar a:contains(Download)").attr("href");
        URI absDownload = uri.resolve(StringUtils.substringBefore(download, "?"));
        moePool.setDownload(download);
        moePool.setAbsdownload(absDownload.toString());
        es = doc.select("#pool-show");
        es = es.get(0).children();// 設成div下面的那一層
        if (es.size() == 3) {// 只有3列的才記錄，2列的不記錄，2列的不包含subtitle
            String subTitle = es.get(1).html().split("\n")[0].trim(); // 本來是用text()，會把換行吃掉，加入會有多餘的資料所以用 換行符號分割subtitle
            if (StringUtils.containsAny(subTitle, "http", "<a")) {// 如果有http或是<a，表示不是真的subtitle而是怪註解，砍掉

            } else if (subTitle.length() <= 6
                    || StringUtils.endsWithAny(subTitle.toLowerCase(), "pireze", "available from", "available from:")) {// 如果長度小於5也不採用
            } else {
                moePool.setTitle2(subTitle);
            }
        }
        String fileName = moePool.getTitle2() == null ? moePool.getTitle1() : moePool.getTitle2();// 主要用subTitle當檔名
        fileName = 共用.處理檔名(fileName);
        File f = 共用.checkFile(fileSavePath, fileName);
        moePool.setFilePath(f.toString());

        捉檔案(absDownload.toString(), Utils.getResourceFromRoot("my/moe.txt"), f);
        System.out.println("新增資料:" + moePool);
        moePoolMapper.insert(moePool);
    }


    public void 捉檔案(String url, File cookieFile, File zipFile) throws Exception {
        String s = FileUtils.readFileToString(cookieFile);
        h.setCookieStore(s);
        h.cookiesHttpTry(url, zipFile, 10);
    }

    public void 用title3重新命名() {
        List<MoePool> list = moePoolMapper.selectByExample(new MoePoolExample());

        for (MoePool moePool : list) {
            String filePath = (String) moePool.getFilePath();
            String title3 = (String) moePool.getTitle3();
            Integer postid = (Integer) moePool.getPostid();
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

    public static boolean 確認要使用title1(String title1, String title2) {
        Pattern pattern = Pattern.compile("[\\u3042-\\u30FC]");//羅馬拼音
        List<String> notInList = Arrays.asList("Sourced", "Artist","a href");


        int use1 = 0;
        int use2 = 0;
        if (StringUtils.isBlank(title2)) {
            return true;
        }
        if (pattern.matcher(title1).find()) {
            use1 += 5;
        }
        if (pattern.matcher(title2).find()) {
            use2 += 3;
        }
        if (StringUtils.length(title1) < StringUtils.length(title2)) {
            use2 += 1;
        }
        if (StringUtils.trimToEmpty(title2).length() < 5) {
            use2 -= 100;
        }
        for (String notIn : notInList) {
            if (StringUtils.trimToEmpty(title2).toUpperCase().contains(notIn.toUpperCase())) {
                use2 -= 1000;
            }
        }
        return use1 >= use2;
    }
}
