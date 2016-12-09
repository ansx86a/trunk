package 爬蟲;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class 輕小說 {

	private File outFile = new File("e:/light");

	public static void main(String args[]) throws Exception {

		// 全部輕小說 // http://www.wenku8.com/modules/article/articlelist.php?class=&page=2
		// 有排行的輕小說//http://www.wenku8.com/modules/article/toplist.php?sort=allvisit&page=2
		// 完結//http://www.wenku8.com/modules/article/articlelist.php?fullflag=1&page=2
		// 動畫化//http://www.wenku8.com/modules/article/toplist.php?sort=anime&page=2
		// http://www.wenku8.com/novel/1/1386/

		// http://novel.slieny.com/modules/article/index.php
		// http://novel.slieny.com/list/_3.html
		// 推見排序
		// http://novel.slieny.com/toplist/allvote-4.html
		幽月輕小說文庫: {

		}

		輕小說文庫動漫區: {
			// 輕小說 a = new 輕小說();
			// for (int i = 9; i < 21; i++) {
			// String url = "http://www.wenku8.com/modules/article/toplist.php?sort=anime";
			// if (i > 1) {
			// url += ("&page=" + i);
			// }
			// a.列表頁(url);
			// }
		}

		System.out.println("end");
	}

	public void 列表頁(String url, boolean isTest) throws IOException, URISyntaxException {
		Document doc = Jsoup.connect(url).get();
		Elements es = doc.select("a:contains(我要阅读)");
		for (Element e : es) {
			String url2 = e.attr("href");
			Document doc2 = Jsoup.connect(url2).get();
			Element e2 = doc2.select("a:contains(小说目录)").get(0);
			String title = doc2.select("title").get(0).text();
			System.out.println(title);
			File f = new File(outFile, 共用.處理檔名(title));

			System.out.println(e2.attr("href"));
			if (isTest) {
				return;
			}
			小說目錄頁(e2.attr("href"), f);
		}

		// <a href="http://www.wenku8.com/book/1657.htm" target="_blank">我要阅读</a>
	}

	public void 小說目錄頁(String url, File f) throws IOException, URISyntaxException {
		Document doc = Jsoup.connect(url).get();
		Elements es = doc.select("tr td");
		URI u = new URI(url);
		String vol = "";
		int i = 0;
		for (Element e : es) {
			// System.out.println(e.outerHtml());
			if (e.attr("class").equals("vcss")) {
				vol = 共用.處理檔名(e.text());
				i = 0;
				continue;
			}
			if (e.text().trim().length() < 2) {
				continue;
			}
			i++;

			System.out.println(vol + "---" + i + e.text());
			String contentUrl = u.resolve(e.getElementsByTag("a").get(0).attr("href")).toString();
			// System.out.println(contentUrl);
			// 處理內容頁("http://www.wenku8.com/novel/1/1657/78474.htm");
			處理內容頁(contentUrl, new File(f, vol), "" + i + 共用.處理檔名(e.text()));
			// System.out.println(u.resolve(e.getElementsByTag("a").get(0).attr("href")));
			// break;
		}
	}

	public void 處理內容頁(String url, File f, String name) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Element e = doc.select("#contentmain").get(0);
		File f2 = new File(f, name + ".html");
		FileUtils.write(f2, e.outerHtml(), "utf-8");
		for (Element img : e.select("img")) {
			String imgStr = img.attr("src");
			System.out.println(imgStr);
			URL u = new URL(imgStr);
			try {
				FileUtils.copyURLToFile(u, new File(f, name + 共用.處理檔名(StringUtils.substringAfterLast(imgStr, "/"))));
			} catch (Exception ex) {
				FileUtils.write(new File(f, name + 共用.處理檔名(StringUtils.substringAfterLast(imgStr, "/")) + "error.txt"),
						"" + u);
			}

		}
	}
}
