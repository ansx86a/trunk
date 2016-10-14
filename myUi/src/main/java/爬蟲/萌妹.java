package 爬蟲;

import http.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.Utils;

public class 萌妹 {

	public static void main(String[] args) throws Exception {
		測試的部分: {
			if (true) {
				break 測試的部分;
			}
			String s = "/pool/show/4228";
			// String ss[] = s.split("/");
			// System.out.println(ss.length);
			// System.out.println(ss[ss.length-1]);
			// System.out.println(Arrays.toString(s.split("/")));
			System.out.println(StringUtils.substringAfterLast(s, "/"));
		}

		網頁的部分: {
			if (false) {
				break 網頁的部分;
			}
			// https://yande.re/pool?page=157
			// https://yande.re/pool?page=1
			String url = "https://yande.re/pool?page=1";
			萌妹 a = new 萌妹();
			a.readlist(url);
		}
		捉檔案的部分: {
			if (true) {
				break 捉檔案的部分;
			}
			String url = "https://yande.re/pool/zip/4219?jpeg=1";
			HttpUtils h = new HttpUtils();
			File f = Utils.getResourceFromRoot("爬蟲/萌妹cookies.txt");
			String s = FileUtils.readFileToString(f);
			h.setCookieStore(s);
			h.cookiesHttp(url, new File("z:/123.zip"));
		}
		System.out.println("end");
	}

	public void save() {

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

			System.out.println("postTitle:" + title);
			System.out.println("相對路徑:" + uriPath);
			System.out.println("絕對路徑:" + uri.resolve(o.attr("href")));
			System.out.println("postNum:" + postNum);
			readPoolPage(absUri);
			readPoolPage(new URI("https://yande.re/pool/show/4219"));
			break;// 先試一筆就好
		}
	}

	// 進入list pool頁
	public void readPoolPage(URI uri) throws IOException {
		Document doc = Jsoup.connect(uri.toString()).get();
		Elements es = null;
		es = doc.select("#pool-show");
		es = es.get(0).children();// 設成div下面的那一層

		// System.out.println(es.size());// 只有3列的才記錄，2列的不記錄
		if (es.size() == 3) {
			String subTitle = es.get(1).text();
			System.out.println(subTitle);
		}
	}

	public String 處理檔名(String s) {
		s = s.replaceAll("`", "‘");
		s = s.replaceAll("~", "～");
		s = s.replaceAll("!", "！");
		s = s.replaceAll("@", "＠");
		s = s.replaceAll("#", "＃");
		s = s.replaceAll("\\$", "＄");
		s = s.replaceAll("%", "％");
		s = s.replaceAll("\\^", "︿");
		s = s.replaceAll("&", "＆");
		s = s.replaceAll("\\*", "＊");
		s = s.replaceAll("\\{", "｛");
		s = s.replaceAll("\\}", "｝");
		s = s.replaceAll("\\|", "｜");
		s = s.replaceAll("\\\\", "＼");
		s = s.replaceAll(":", "：");
		s = s.replaceAll(";", "；");
		s = s.replaceAll("\"", "”");
		s = s.replaceAll("'", "’");
		s = s.replaceAll("<", "＜");
		s = s.replaceAll(">", "＞");
		s = s.replaceAll("\\?", "？");
		s = s.replaceAll(",", "，");
		// s=s.replaceAll("\\.", "．");
		s = s.replaceAll("/", "／");
		return s;
	}

}
