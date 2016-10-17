package 爬蟲;

import http.HttpUtils;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class 紳士漫畫 {

	public static void main(String[] args) throws Exception {
		日語單行本: {
			// http://www.wnacg.org/albums-index-page-1-cate-13.html
			紳士漫畫 a = new 紳士漫畫();
			String url = "http://www.wnacg.org/albums-index-page-1-cate-13.html";
			a.本子list頁(url);
		}

		漫畫自然排序法重新命名: {
			if (true) {
				break 漫畫自然排序法重新命名;
			}
			File dir = new File("z:/1");
			TreeSet<String> set = new TreeSet<String>();
			for (File f : dir.listFiles()) {
				set.add(f.getAbsolutePath());
			}

			int i = 1;
			for (String s : set) {
				File f = new File(s);
				String sub = StringUtils.substringAfter(s, ".");
				File f2 = new File("z:/2/" + String.format("%05d." + sub, i++));
				System.out.println("" + f + "<F-->" + f2);
				FileUtils.copyFile(f, f2);
			}
		}

		System.out.println("end");

	}

	public void 本子list頁(String url) throws Exception {
		Document doc = Jsoup.connect(url).get();
		URI uri = new URI(url);
		Elements es = null;
		es = doc.select(".search_result_box");
		for (Element o : es) {
			String title = o.select("p.title_name").get(0).text();
			String uriPath = o.select("a.comic_list_view").get(0).attr("href");
			URI absUri = uri.resolve(uriPath);

			System.out.println("postTitle:" + title);
			System.out.println("相對路徑:" + uriPath);
			System.out.println("絕對路徑:" + absUri);

			// 試跑一次就好
			漫畫list頁(absUri.toString());
			break;
		}

	}

	public void 漫畫list頁(String url) throws Exception {
		Document doc = Jsoup.connect(url).get();
		URI uri = new URI(url);
		String downloadPage = doc.select("a:contains(下載本子)").attr("href");
		System.out.println(downloadPage);
		System.out.println(uri.resolve(downloadPage));

		doc = Jsoup.connect(uri.resolve(downloadPage).toString()).get();
		Elements es = doc.select("a:contains(本地下載)");
		//發現本地下載1和2都是一樣的位扯，所以就用1就好了
		System.out.println(es.attr("href"));

	}

}
