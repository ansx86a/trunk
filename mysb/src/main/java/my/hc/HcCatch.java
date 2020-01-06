package my.hc;

import dao.HcomicPoolMapper;
import dao.domain.HcomicPool;
import dao.domain.HcomicPoolExample;
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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

@Controller
@RequestMapping("hc")
public class HcCatch {
    enum 爬蟲type {
        單行本("1"), 雜誌("2"), 同人和cosplay("3");
        String value;

        爬蟲type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    private 爬蟲type t = 爬蟲type.單行本;
    private String fileSavePath = "d:/moe/hcomic";
    private int[] skipComic = new int[]{};
    private Integer type = Integer.parseInt(t.toString());// 1:單行本2:雜誌3同人&cosplay
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";

    @Autowired
    private HcomicPoolMapper hcomicPoolMapper;

    @RequestMapping("main")
    public String main() throws Exception {
        HcCatch a = this;
        單行本爬蟲:
        {
            if (false) {// false=只爬蟲不下載，一頁有12個項目
                break 單行本爬蟲;
            }

            if (a.t == 爬蟲type.單行本) {
                for (int i = 1; i <= 50; i++) {// 2018/04/20
                    System.out.println("now--" + i);
                    // http://www.wnacg.org/albums-index-page-1-cate-13.html//日文
                    // http://www.wnacg.org/albums-index-page-2-cate-6.html//中日文
                    String url = "http://www.wnacg.org/albums-index-page-" + i + "-cate-6.html";
                    a.本子list頁(url);
                }
            } else if (a.t == 爬蟲type.雜誌) {// 306//2016/10/31
                for (int i = 1; i <= 310; i++) {
                    System.out.println("now--" + i);
                    String url = "http://www.wnacg.org/albums-index-page-" + i + "-cate-7.html";
                    a.本子list頁(url);
                }
            } else if (a.t == 爬蟲type.同人和cosplay) {
                for (int i = 600; i <= 1000; i++) {// 1731//2016/10/31
                    String url = "http://www.wnacg.org/albums-index-page-" + i + "-cate-5.html";
                    System.out.println("now--" + i);
                    a.本子list頁(url);
                }
            }
            return "myPage";
        }
        單行本下載:
        {
            a.單行本下載();
        }
        漫畫自然排序法重新命名:
        {
            if (true) {
                break 漫畫自然排序法重新命名;
            } else {
                File souceDir = new File("z:/1");
                File destDir = new File("z:/2");
                漫畫自然排序法重新命名(souceDir, destDir);
            }
        }

        System.out.println("end");

        return "myPage";
    }

    public static void 漫畫自然排序法重新命名(File souceDir, File destDir) throws IOException {
        TreeSet<String> set = new TreeSet<String>();
        for (File f : souceDir.listFiles()) {
            set.add(f.getAbsolutePath());
        }
        int i = 1;
        for (String s : set) {
            File f = new File(s);
            String sub = StringUtils.substringAfterLast(s, ".");
            File f2 = new File(destDir, String.format("%05d." + sub, i++));
            // File f2 = new File("z:/2/" + String.format("%05d." + sub, i++));
            System.out.println("" + f + "<F-->" + f2);
            FileUtils.copyFile(f, f2);
        }
    }

    public void 本子list頁(String url) throws Exception {
        System.out.println(url);
        Document doc = Jsoup.connect(url).timeout(100000).userAgent(USER_AGENT).get();
        URI uri = new URI(url);
        Elements es = null;
        es = doc.select(".gallary_item");
        for (Element o : es) {
            String title = o.select("div.title>a").get(0).text();
            System.out.println(title);
            // if (title.equals("[Pつssy汉化组-062] (C86) [H.B.A (うさぎなごむ)] 搾り魔女 (オリジナル)")) {
            // System.out.println(title);
            // continue;
            // }

            // String uriPath = o.select("a.comic_list_view").get(0).attr("href");
            String uriPath = o.select("div.title>a").get(0).attr("href");
            URI absUri = uri.resolve(uriPath);
            String postid = StringUtils.substringBefore(StringUtils.substringAfterLast(uriPath, "-"), ".");
            HcomicPoolExample ex = new HcomicPoolExample();
            ex.createCriteria().andComicidEqualTo(Integer.parseInt(postid))
                    .andTitle1EqualTo(title)
                    .andUrlEqualTo(uriPath)
                    .andAbsurlEqualTo(absUri.toString())
                    .andTypeEqualTo(type);
            HcomicPool map = new HcomicPool();
            map.setComicid(Integer.parseInt(postid));
            map.setTitle1(title);
            map.setUrl(uriPath);
            map.setAbsurl(absUri.toString());
            map.setType(type);
            if (hcomicPoolMapper.selectByExample(ex).size() > 0) {
                System.out.println("檔案已存在:" + map);
            } else if (Arrays.binarySearch(skipComic, Integer.parseInt(postid)) >= 0) {
                System.out.println("跳過無法下載的zip檔：" + postid);
            } else {
                漫畫list頁(absUri.toString(), map);
            }
        }

    }

    public void 漫畫list頁(String url, HcomicPool map) throws Exception {
        System.out.println(url);
        Document doc = Jsoup.connect(url).timeout(100000).userAgent(USER_AGENT).get();
        URI uri = new URI(url);
        //String downloadPage = doc.select("a:contains(下載本子)").attr("href");
        //String downloadPage = doc.select("a.downloadbtn").attr("href");

        //String absDownloadPage = uri.resolve(downloadPage).toString();
        String absDownloadPage = uri.toString().replace("photos","download");
        System.out.println(absDownloadPage);
        doc = Jsoup.connect(absDownloadPage).timeout(100000).userAgent(USER_AGENT).get();
        Elements es = doc.select("a:contains(本地下載)");
        // 發現本地下載1和2都是一樣的位扯，所以就用1就好了
        String downloadFile = es.attr("href");
        map.setAbsdownload(downloadFile);

        String fileName = 共用.處理檔名(map.getTitle1());
        File outFile = 共用.checkFile(fileSavePath, fileName);
        map.setFilePath(outFile.toString());
        System.out.println(map);
        // 這裡捉檔案
        // HttpUtils.httpTry(downloadFile, outFile, 10);
        // 存到db
        map.setDownloaded(0);
        hcomicPoolMapper.insert(map);
    }

    public void 單行本下載() {
        HcomicPoolExample ex = new HcomicPoolExample();
        ex.createCriteria().andDownloadedEqualTo(0).andTypeEqualTo(type);
        List<HcomicPool> list = hcomicPoolMapper.selectByExample(ex);
        list.parallelStream().forEach(o -> {
            try { // 一次下載4個，哈哈哈，錯一個就直接崩潰
                String title = o.getTitle1();
                String downloadUrl = o.getAbsdownload();
                String fileName = 共用.處理檔名(title);
                File outFile = 共用.checkFile(fileSavePath, fileName);
                o.setFilePath(outFile.toString());
                HttpUtils.httpTry(downloadUrl, outFile, 10);
                o.setDownloaded(1);
                hcomicPoolMapper.updateByPrimaryKey(o);
                System.out.println("更新db ok:" + o);
            } catch (Exception ex1) {
                String exStr = ex1.toString() + ("\r\n" + o.toString());
                // throw new RuntimeException(ex);
                File outFile = new File(fileSavePath, "comicError_" + o.getComicid() + ".txt");
                try {
                    FileUtils.write(outFile, exStr);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
