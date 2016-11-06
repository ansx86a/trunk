package 爬蟲;

import http.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.Utils;
import db.SqlDao;

public class Ex紳士 {

	enum Extype {
		爬蟲("1"), 捉圖("2");
		String value;

		Extype(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	private Extype type = Extype.爬蟲;
	private HttpUtils h = new HttpUtils();
	// 檔案相關
	private String fileSavePath = "e:/moe/ex";
	private File outDir;
	private HashMap row;

	// 同步操作相關
	public static Object obj = new Object();
	private long 時間間隔 = 4_000;
	public Date d = new Date(System.currentTimeMillis() + 時間間隔);
	public Random rand = new Random();

	public static void main(String[] args) throws Exception {
		// 設定可並行的管線數目，設x就程示同時跑x+1個，不用參數的話要研究ForkJoinPool，太麻煩了
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");

		Ex紳士 ex = new Ex紳士();
		ex.init();
		if (ex.type == Extype.爬蟲) {// 從20頁開始捉，我不想捉到有上傳到一半的
			for (int i = 100; i < 120; i++) {
				String url = "https://exhentai.org/?page=" + i+"&f_doujinshi=on&f_manga=on&f_gamecg=on&f_non-h=on&f_apply=Apply+Filter";
				ex.讀取文章列表(url);
				Thread.sleep(2000);// 每個主頁分開2秒，才不會讀太快
			}
			return;
		}
		if (ex.type == Extype.捉圖) {
			// nextUrl = "https://exhentai.org/g/992806/2d46195697/";// 先用來測試一下
			// readnext(nextUrl);
			HashMap map = new HashMap();
			map.put("downloaded", 0);
			map.put("looked", 1);
			List<HashMap> list = SqlDao.get().撈取ex資料(map);

			Comparator<HashMap> c = (x, y) -> {
				return (int) y.get("exid") - (int) x.get("exid");// 大的在上面
				// return (int) x.get("exid") - (int) y.get("exid");//小的在上面
			};
			// list = list.stream().sorted(c).collect(Collectors.toList());
			list.sort(c);
			for (HashMap m : list) {
				System.out.println(m);
				String nextUrl = m.get("url").toString();
				if (-1 == (int) m.get("looked")) {// 已經忽略的列表
					continue;
				}
				ex.row = m;
				String dir = 共用.處理檔名(m.get("title1").toString());
				System.out.println(dir);
				ex.outDir = 共用.checkFile(ex.fileSavePath, dir, "");
				System.out.println(ex.outDir);
				ex.圖檔列表頁面(nextUrl);
				HashMap updateMap = new HashMap();
				updateMap.put("downloaded", "1");
				updateMap.put("exid", m.get("exid"));
				SqlDao.get().更新ex資料(updateMap);
			}
		}
	}

	public void init() throws IOException {
		String cookieStr = FileUtils.readFileToString(Utils.getResourceFromRoot("爬蟲/Ex紳士cookies.txt"));
		h.setCookieStore(cookieStr);
	}

	public void 讀取文章列表(String url) throws IOException {
		String result = h.cookiesHttp(url);// 讀取
		Document doc = Jsoup.parse(result);
		Elements es = doc.select(".it5 a");
		for (Element e : es) {
			// System.out.println(e.outerHtml());// 這裡可以取得網扯和名字
			String nextUrl = e.attr("href");
			String exid = nextUrl.split("/")[4];
			String title = e.text();
			HashMap map = new HashMap();
			map.put("exid", exid);
			map.put("title1", title);
			map.put("url", nextUrl);
			System.out.print(map);
			List<HashMap> list = SqlDao.get().撈取ex資料(map);
			if (list.size() > 0) {
				System.out.println("------已經存在了");
			} else {
				System.out.println("inserting");
				SqlDao.get().新增一筆ex資料(map);
			}
		}
	}

	public void 圖檔列表頁面(String nextUrl) throws Exception {

		String result = "";
		for (int i = 0; i < 10; i++) {
			try {
				result = h.cookiesHttp(nextUrl);
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Thread.sleep(3000);
		}
		if (result == "") {
			String newLog = outDir.getAbsolutePath() + "listError" + "_" + new Date().getTime() / 1000 % 3600 + ".txt";
			File newLogFile = new File(newLog);
			String log = "" + row.get("url") + "\r\n" + nextUrl;
			FileUtils.writeStringToFile(newLogFile, log);
			return;
		}

		nextUrl = "";// 初始化，用來遞迴判斷
		Document doc = Jsoup.parse(result);
		Elements es0 = doc.select("table.ptt td");
		if (es0.size() > 0) {
			Element e0 = es0.get(es0.size() - 1);
			if (StringUtils.equals(e0.attr("class"), "ptdd")) {
				System.out.println("最後一頁");
			} else {
				nextUrl = e0.select("a").attr("href");
			}
		}

		Elements es = doc.select(".gdtl a");
		es.parallelStream().forEach(e -> {
			String imgUrl = e.attr("href");
			String page = e.text();
			System.out.println("imgUrl=" + imgUrl);
			System.out.println("page = " + page);
			try {
				圖檔頁(imgUrl, page);
			} catch (Exception ex) {
				System.out.println("管線操作發生ex");
				ex.printStackTrace();
			}
		});

		// for (Element e : es) {
		// // System.out.println(e.outerHtml());// 這裡可以取得網扯和名字
		// String imgUrl = e.attr("href");
		// String page = e.text();
		// System.out.println("imgUrl=" + imgUrl);
		// System.out.println("page = " + page);
		// 圖檔頁(imgUrl, page);
		// }
		if (StringUtils.isNotBlank(nextUrl)) {
			圖檔列表頁面(nextUrl);// 遞迴
		}
	}

	public void 圖檔頁(String imgUrl, String page) throws Exception {
		String newPage = String.format("%05d", Integer.parseInt(page));
		String result = "";
		for (int i = 0; i < 5; i++) {
			try {
				doThreadSleep();
				result = h.cookiesHttp(imgUrl);
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (result == "") {
			String newLog = outDir.getAbsolutePath() + "pageError_" + page + "_" + new Date().getTime() / 1000 % 3600
					+ ".txt";
			File newLogFile = new File(newLog);
			String log = "" + row.get("url") + "\r\n" + imgUrl;
			FileUtils.writeStringToFile(newLogFile, log);
			return;
		}

		Document doc = Jsoup.parse(result);
		String imgSrc = doc.select("#i3 img").attr("src");
		String info = doc.select("#i4").text();
		String fail = doc.select("#loadfail").attr("onclick");
		fail = StringUtils.substringBefore(StringUtils.substringAfter(fail, "'"), "'");
		System.out.println(imgSrc);
		System.out.println(info);
		// 存圖檔
		File outFile = 共用.checkFile(outDir.getAbsolutePath(),
				newPage + "_" + StringUtils.substringAfterLast(imgSrc, "/"), "");
		// File outFile = new File(outDir, StringUtils.substringAfterLast(imgSrc, "/"));
		try {
			HttpUtils.httpTry(imgSrc, outFile, 1);
		} catch (Exception ex) {
			if (imgUrl.indexOf("?nl=") < 0) {
				圖檔頁(imgUrl + "?nl=" + fail, page);
				return;
			}

			String newLog = outDir.getAbsolutePath() + "imgError_" + StringUtils.substringAfterLast(imgSrc, "/") + "_"
					+ new Date().getTime() / 1000 % 3600 + ".txt";
			File newLogFile = new File(newLog);
			String log = "" + row.get("url") + "\r\n" + info + "\r\n" + imgSrc;
			FileUtils.writeStringToFile(newLogFile, log);
		}
	}

	public void doThreadSleep() throws Exception {
		while (true) {
			//System.out.println(Thread.currentThread().getId() + "-" + new Date(System.currentTimeMillis()));
			synchronized (obj) {
				if (System.currentTimeMillis() - d.getTime() > 0) {
					d = new Date(System.currentTimeMillis() + 時間間隔 - 1_000 + rand.nextInt(2000));// 弄亂時間差
					System.out.println("下一次可執行時間" + Thread.currentThread().getId() + "-" + d);
					break;
				}
			}
			Thread.sleep(300 + rand.nextInt(300));
		}
		System.out.println("執行時間:" + Thread.currentThread().getId() + "-" + new Date(System.currentTimeMillis()));
	}
}
