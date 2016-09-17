package ui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class 漫畫爬蟲 {

	public static void main(String[] a) throws Exception {
		// http://www.cartoonmad.com/
		動漫狂測試: {
			// String url = "http://www.cartoonmad.com/comic/1429.html";
			// 動漫狂目錄頁(url);
		}
		// http://www.ikanman.com/

		愛看漫測試: {
			// http://www.ikanman.com/comic/4773/
			// http://www.ikanman.com/comic/4773/43867.html
			Path p=new File("z:/comic/1").toPath();
			String url = "http://www.ikanman.com/comic/4773/43867.html#p=8";
			愛看漫漫畫頁捉圖(url, p);

		}

		System.out.println("end");

	}

	public static void 愛看漫目錄頁(String url) {

	}

	public static void 愛看漫漫畫頁(String url, String dir) {

	}

	public static void 愛看漫漫畫頁捉圖(String url, Path dirPath) throws IOException, URISyntaxException {
		System.out.println(url);
		Document doc = null;
		for (int i = 0; i < 10; i++) {
			try {
				doc = Jsoup.connect(url).get();
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		Elements es = null;
		es = doc.select("#mangaBox");
		System.out.println(es.size());
		System.out.println(es.get(0).html());
		if (es.size() != 1) {
			System.out.println("error==================");
			System.out.println(es.size());
		}
		String s = es.get(0).attr("src");
		System.out.println(s);
		for (int i = 0; i < 10; i++) {
			try {
				byte[] bs = IOUtils.toByteArray(new URI(s));
				s = s.substring(s.lastIndexOf("/") + 1);
				FileUtils.writeByteArrayToFile(dirPath.resolve(s).toFile(), bs);
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void 動漫狂目錄頁(String url) throws IOException, URISyntaxException {
		String startStr = "028";
		boolean flag = true;

		Document doc = Jsoup.connect(url).get();
		URI uri = new URI(url);
		Elements es = null;
		es = doc.select("a[href~=comic]:contains(第)");
		for (Element o : es) {
			System.out.println(o.text());
			System.out.println(o.attr("href"));
			System.out.println(uri.resolve(o.attr("href")));
			if (flag || StringUtils.contains(o.text(), startStr)) {
				flag = true;
				動漫狂漫畫頁(uri.resolve(o.attr("href")).toString(), o.text());
			}
		}
	}

	public static void 動漫狂漫畫頁(String url, String dir) throws IOException, URISyntaxException {
		Path path = Paths.get("z:/comic", dir);

		Document doc = Jsoup.connect(url).get();
		URI uri = new URI(url);
		Elements es = null;
		es = doc.select("select[name='jump'] option");
		// 在這裡先用一個list把資料收集起來之後，再用java8 fifter的功能做多執行緒的捉圖
		es.parallelStream().forEach(e -> {
			System.out.println(e.attr("value"));
			String page = e.attr("value");
			if (StringUtils.trim(page).length() < 5) {// 理由.html 5碼
				return;
			}
			page = uri.resolve(page).toString();
			try {
				動漫狂漫畫頁捉圖(page, path);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// for (Element e : es) {
		// }
	}

	public static void 動漫狂漫畫頁捉圖(String url, Path dirPath) throws IOException, URISyntaxException {
		System.out.println(url);
		Document doc = null;
		for (int i = 0; i < 10; i++) {
			try {
				doc = Jsoup.connect(url).get();
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		Elements es = null;
		es = doc.select("a>img[src~=cartoo");
		if (es.size() != 1) {
			System.out.println("error==================");
			System.out.println(es.size());
		}
		String s = es.get(0).attr("src");
		System.out.println(s);
		for (int i = 0; i < 10; i++) {
			try {
				byte[] bs = IOUtils.toByteArray(new URI(s));
				s = s.substring(s.lastIndexOf("/") + 1);
				FileUtils.writeByteArrayToFile(dirPath.resolve(s).toFile(), bs);
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
