package 爬蟲;

import http.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import db.SqlDao;

public class 紳士漫畫 {
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

	private 爬蟲type t = 爬蟲type.同人和cosplay;
	private String fileSavePath = "e:/moe/hcomic";
	private int[] skipComic = new int[] {};
	private String type = t.toString();// 1:單行本2:雜誌3同人&cosplay

	public static void main(String[] args) throws Exception {
		單行本爬蟲: { // 改成只爬蟲不下載，一頁有12個項目
			if (true) {
				break 單行本爬蟲;
			}

			紳士漫畫 a = new 紳士漫畫();
			if (a.t == 爬蟲type.單行本) {
				for (int i = 1; i <= 345; i++) {// 2016/10/31
					// http://www.wnacg.org/albums-index-page-1-cate-13.html//日文
					// http://www.wnacg.org/albums-index-page-2-cate-6.html//中日文
					String url = "http://www.wnacg.org/albums-index-page-" + i + "-cate-6.html";
					a.本子list頁(url);
				}
			} else if (a.t == 爬蟲type.雜誌) {// 306//2016/10/31
				for (int i = 1; i <= 310; i++) {
					String url = "http://www.wnacg.org/albums-index-page-" + i + "-cate-7.html";
					a.本子list頁(url);
				}
			} else if (a.t == 爬蟲type.同人和cosplay) {
				for (int i = 1250; i <= 1750; i++) {// 1731//2016/10/31
					String url = "http://www.wnacg.org/albums-index-page-" + i + "-cate-5.html";
					System.out.println(url);
					a.本子list頁(url);
				}
			}
			return;
		}
		單行本下載: {
			紳士漫畫 a = new 紳士漫畫();
			a.單行本下載();
		}
		漫畫自然排序法重新命名: {
			if (true) {
				break 漫畫自然排序法重新命名;
			} else {
				File souceDir = new File("z:/1");
				File destDir = new File("z:/2");
				漫畫自然排序法重新命名(souceDir, destDir);
			}
		}

		System.out.println("end");

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
		Document doc = Jsoup.connect(url).get();
		URI uri = new URI(url);
		Elements es = null;
		es = doc.select(".search_result_box");
		for (Element o : es) {
			String title = o.select("p.title_name").get(0).text();
			System.out.println(title);
			// if (title.equals("[Pつssy汉化组-062] (C86) [H.B.A (うさぎなごむ)] 搾り魔女 (オリジナル)")) {
			// System.out.println(title);
			// continue;
			// }

			String uriPath = o.select("a.comic_list_view").get(0).attr("href");
			URI absUri = uri.resolve(uriPath);
			String postid = StringUtils.substringBefore(StringUtils.substringAfterLast(uriPath, "-"), ".");
			HashMap map = new HashMap<>();
			map.put("comicid", postid);
			map.put("title1", title);
			map.put("url", uriPath);
			map.put("absurl", absUri.toString());
			map.put("type", this.type);
			if (確認是否已存在(map)) {
				System.out.println("檔案已存在:" + map);
			} else if (Arrays.binarySearch(skipComic, Integer.parseInt(postid)) >= 0) {
				System.out.println("跳過無法下載的zip檔：" + postid);
			} else {
				漫畫list頁(absUri.toString(), map);
			}
		}

	}

	public void 漫畫list頁(String url, HashMap map) throws Exception {
		Document doc = Jsoup.connect(url).get();
		URI uri = new URI(url);
		String downloadPage = doc.select("a:contains(下載本子)").attr("href");
		String absDownloadPage = uri.resolve(downloadPage).toString();

		doc = Jsoup.connect(absDownloadPage).get();
		Elements es = doc.select("a:contains(本地下載)");
		// 發現本地下載1和2都是一樣的位扯，所以就用1就好了
		String downloadFile = es.attr("href");
		map.put("absdownload", downloadFile);
		String fileName = 共用.處理檔名((String) map.get("title1"));
		File outFile = 共用.checkFile(fileSavePath, fileName);
		map.put("file_path", outFile.toString());
		System.out.println(map);
		// 這裡捉檔案
		// HttpUtils.httpTry(downloadFile, outFile, 10);
		// 存到db
		SqlDao.get().新增一筆紳士comic資料(map);
	}

	public boolean 確認是否已存在(HashMap map) {
		if (map.get("comicid") == null || Integer.parseInt(map.get("comicid").toString()) <= 0) {
			throw new RuntimeException("comicid錯誤=" + map.get("comicid"));
		}
		HashMap map2 = new HashMap();
		map2.put("comicid", map.get("comicid"));
		List<HashMap> list = SqlDao.get().撈取紳士comic資料(map2);
		return list.size() >= 1;
	}

	public void 單行本下載() {
		HashMap map = new HashMap<>();
		map.put("downloaded", "0");
		map.put("type", this.type);
		List<HashMap> list = SqlDao.get().撈取紳士comic資料(map);
		list.parallelStream().forEach(o -> {
			try { // 一次下載4個，哈哈哈，錯一個就直接崩潰
					String title = (String) o.get("title1");
					String downloadUrl = (String) o.get("absdownload");
					String fileName = 共用.處理檔名(title);
					File outFile = 共用.checkFile(fileSavePath, fileName);
					o.put("file_path", outFile.toString());
					HttpUtils.httpTry(downloadUrl, outFile, 10);
					o.put("downloaded", "1");
					SqlDao.get().更新紳士comic資料(o); // 更新db為下載完成
					System.out.println("更新db ok:" + o);
				} catch (Exception ex) {
					String exStr = ex.toString() + ("\r\n" + o.toString());
					// throw new RuntimeException(ex);
					File outFile = new File(fileSavePath, "comicError_" + o.get("comicid") + ".txt");
					try {
						FileUtils.write(outFile, exStr);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});

	}

}
