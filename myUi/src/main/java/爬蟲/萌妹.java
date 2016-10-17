package 爬蟲;

import http.HttpUtils;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.Utils;
import db.SqlDao;

public class 萌妹 {
	private HttpUtils h = new HttpUtils();
	private static int[] skipPost = new int[] { 2057, 2924, 4098 };

	public static void main(String[] args) throws Exception {
		用title3重新命名: {
			if (1 == 0) {
				break 用title3重新命名;
			}
			萌妹 a = new 萌妹();
			a.用title3重新命名();
			return;
		}

		網頁的部分: {
			// https://yande.re/pool?page=157
			// https://yande.re/pool?page=1
			萌妹 a = new 萌妹();
			for (int i = 70; i <= 100; i++) {
				String url = "https://yande.re/pool?page=" + i;
				a.readlist(url);
			}
		}
		// 捉檔案的部分: {
		// if (true) {
		// break 捉檔案的部分;
		// }
		// String url = "https://yande.re/pool/zip/4219?jpeg=1";
		// HttpUtils h = new HttpUtils();
		// File f = Utils.getResourceFromRoot("爬蟲/萌妹cookies.txt");
		// String s = FileUtils.readFileToString(f);
		// h.setCookieStore(s);
		// h.cookiesHttp(url, new File("z:/123.zip"));
		// }
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

			HashMap map = new HashMap<>();
			map.put("postid", postNum);
			map.put("title1", title);
			map.put("url", uriPath);
			map.put("absurl", absUri.toString());
			System.out.println(postNum + ":" + Arrays.binarySearch(skipPost, Integer.parseInt(postNum)));
			if (確認是否已存在(map)) {
				System.out.println("檔案已存在:" + map);
			} else if (Arrays.binarySearch(skipPost, Integer.parseInt(postNum)) >= 0) {
				System.out.println("跳過無法下載的zip檔：" + postNum);
			} else {
				readPostPage(absUri, map);
			}
		}
	}

	// 進入list pool頁
	public void readPostPage(URI uri, HashMap map) throws Exception {
		Document doc = Jsoup.connect(uri.toString()).get();
		Elements es = null;
		String download = doc.select("#subnavbar a:contains(Download)").attr("href");
		URI absDownload = uri.resolve(StringUtils.substringBefore(download, "?"));
		map.put("download", download);
		map.put("absdownload", absDownload.toString());
		es = doc.select("#pool-show");
		es = es.get(0).children();// 設成div下面的那一層
		if (es.size() == 3) {// 只有3列的才記錄，2列的不記錄，2列的不包含subtitle
			String subTitle = es.get(1).html().split("\n")[0].trim(); // 本來是用text()，會把換行吃掉，加入會有多餘的資料所以用 換行符號分割subtitle
			if (StringUtils.containsAny(subTitle, "http", "<a")) {// 如果有http或是<a，表示不是真的subtitle而是怪註解，砍掉

			} else if (subTitle.length() <= 6
					|| StringUtils.endsWithAny(subTitle.toLowerCase(), "pireze", "available from", "available from:")) {// 如果長度小於5也不採用
			} else {
				map.put("title2", subTitle);
			}
		}
		String fileName = map.get("title2") == null ? map.get("title1").toString() : map.get("title2").toString();// 主要用subTitle當檔名
		fileName = 處理檔名(fileName);
		File f = checkFile(fileName);
		map.put("file_path", f.toString());

		捉檔案(absDownload.toString(), Utils.getResourceFromRoot("爬蟲/萌妹cookies.txt"), f);
		System.out.println("新增資料:" + map);
		SqlDao.get().新增一筆moePost資料(map);
	}

	/**
	 * 確保不要檔名重覆去蓋到
	 * @param fileName
	 * @return
	 */
	public File checkFile(String fileName) {
		for (int i = 0; i <= 50; i++) {
			String append = i < 1 ? "" : String.format("%03d", i);// i=0時fileName不變，其它的時候帶i的值
			File f = new File("e:/moe/post", fileName + append + ".zip");
			if (!f.exists()) {
				return f;
			}
		}
		return null;
	}

	public boolean 確認是否已存在(HashMap map) {
		if (map.get("postid") == null || Integer.parseInt(map.get("postid").toString()) <= 0) {
			throw new RuntimeException("postid錯誤=" + map.get("postid"));
		}
		HashMap map2 = new HashMap();
		map2.put("postid", map.get("postid"));
		List<HashMap> list = SqlDao.get().撈取moePost資料(map2);
		return list.size() >= 1;
	}

	public void 捉檔案(String url, File cookieFile, File zipFile) throws Exception {
		String s = FileUtils.readFileToString(cookieFile);
		h.setCookieStore(s);
		h.cookiesHttpTry(url, zipFile, 10);
	}

	public void 用title3重新命名() {
		HashMap map2 = new HashMap();
		List<HashMap> list = SqlDao.get().撈取moePost資料(map2);
		for (HashMap<String, Object> map : list) {
			String filePath = (String) map.get("file_path");
			String title3 = (String) map.get("title3");
			Integer postid = (Integer) map.get("postid");
			if (postid > 2810) {// 因為有好幾批下載，會有檔名重覆的問題，所以以postid區間來當成重新命名的依據
				continue;
			}

			if (StringUtils.isNotBlank(title3)) {
				title3 = 處理檔名(title3);
				File f = new File(filePath);
				File newFile = checkFile(title3);
				if (f.exists() && !newFile.exists()) {// 舊檔存在，新檔不存在
					f.renameTo(newFile);
					System.out.println("" + newFile + "--" + f);
				}
			}
		}
	}

	/**
	 * 處理window不合法的檔名成全型
	 * @param s
	 * @return
	 */
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
