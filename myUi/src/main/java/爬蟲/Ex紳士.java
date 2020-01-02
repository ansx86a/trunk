package 爬蟲;

import http.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.Utils;
import clientServer.Irmi;
import clientServer.RMIClient;
import db.SqlDao;


public class Ex紳士 {

enum Site {
	e, ex;
	public String toString() {
		if (this == e) {
			return "https://e-hentai.org";
		}
		if (this == ex) {
			return "https://exhentai.org";
		}
		return "";
	}
}
	enum Extype {
		爬蟲, 捉圖, 檢查不下載,
	}

	enum DownloadMode {
		重新下載, 接下去下載
	}

	enum RemoteMode {
		本地, 遠端
	}

	private static List notDownloadList = Arrays.asList("ta_male:yaoi");
	private static Site site = Site.ex;
	private Extype type = Extype.捉圖;
	private DownloadMode downloadMode = DownloadMode.重新下載;
	private RemoteMode remoteMode = RemoteMode.本地;
	private HttpUtils h = new HttpUtils();
	// 檔案相關
	private String fileSavePath = "d:/moe/ex";
	private File outDir;
	private HashMap row;

	// 同步操作相關
	public static Object obj = new Object();
	private long count = 0;
	private long stopCount = 46000;
	private long 時間間隔 = 2_000;
	public Date d = new Date(System.currentTimeMillis() + 時間間隔);
	public Random rand = new Random();

	public Date stopDate = DateUtils.addMinutes(new Date(), 1 * 60 * 24);// 多久停止

	public static void main(String[] args) throws Exception {
		// 設定可並行的管線數目，設x就程示同時跑x+1個，不用參數的話要研究ForkJoinPool，太麻煩了
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "5");

		Ex紳士 ex = new Ex紳士();
		ex.init();
		if (ex.type == Extype.爬蟲) {// 從20頁開始捉，我不想捉到有上傳到一半的
			// for (int i = 200; i < 300; i++) {
			// String url = "https://exhentai.org/?page=" + i
			// + "&f_doujinshi=on&f_manga=on&f_gamecg=on&f_non-h=on&f_apply=Apply+Filter";
			// System.out.println(url);
			// ex.讀取文章列表(url);
			// Thread.sleep(2000);// 每個主頁分開2秒，才不會讀太快
			// }
			// 開始200-300
			// ex到了5300
			// for (int i = 5200; i <= 5300; i++) {// 感覺有很多莫名的資料，很古怪，順序有問題？改天全部重掃嗎？
			 for (int i = 6700; i <= 6900; i++) {// 感覺有很多莫名的資料，很古怪，順序有問題？改天全部重掃嗎？
//			for (int i = 200; i <= 300; i++) {
				// String url = "https://exhentai.org/?page=" + i;
				String url = site == Site.ex ? "https://exhentai.org/?page=" + i
						: "https://e-hentai.org/?page=" + i + "&f_cats=745";
				// 下面是舊的url
				// String url = "https://exhentai.org/?page=" + i
				// + "&f_doujinshi=on&f_manga=on&f_gamecg=on&f_non-h=on&f_apply=Apply+Filter";
				// https://exhentai.org/?page=1&f_doujinshi=on&f_manga=on&f_gamecg=on&f_non-h=on&f_apply=Apply+Filter

				System.out.println(url);

				// try {
				ex.讀取文章列表(url);
				// }catch (Exception eex) {
				// eex.printStackTrace();
				// }

				Thread.sleep(5_000);// 每個主頁分開2秒，才不會讀太快
			}
			System.out.println("end");
			return;
		}

		if (ex.type == Extype.檢查不下載) {
			List<HashMap> list = 列表未下載已排序的網扯();
			int i = 0;
			i++;
			System.out.println(i);
		}

		if (ex.type == Extype.捉圖) {
			List<HashMap> list = 列表未下載已排序的網扯();
			for (@SuppressWarnings("rawtypes")
			HashMap m : list) {
				if (ex.stopDate.before(new Date())) {
					System.out.println("超過執行時間，強制停止");
					return;
				}

				System.out.println(m);
				String nextUrl = m.get("url").toString();
				if (-1 == (int) m.get("looked")) {// 已經忽略的列表
					continue;
				}
				ex.row = m;
				String dir = 共用.處理檔名(m.get("title1").toString());
				// dir += "(exid_" + m.get("exid") + ")";
				System.out.println(dir);
				ex.outDir = 共用.checkFile(ex.fileSavePath, dir, "(exid_" + m.get("exid") + ")");
				System.out.println(ex.outDir);
				if (site == Site.e) {
					nextUrl = nextUrl.replaceFirst(Pattern.quote(Site.ex.toString()), Site.e.toString());
				}
				ex.圖檔列表頁面(nextUrl);

				HashMap updateMap = new HashMap();
				updateMap.put("downloaded", "1");
				updateMap.put("exid", m.get("exid"));
				SqlDao.get().更新ex資料(updateMap);

			}
		}
		System.out.println("end");
	}

	private static List<HashMap> 列表未下載已排序的網扯() {
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
		System.out.println("total count:" + list.size());
		return list;
	}

	public void init() throws IOException {
		String path = site == Site.ex ? "爬蟲/Ex紳士cookies.txt" : "爬蟲/E紳士cookies.txt";
		String cookieStr = FileUtils.readFileToString(Utils.getResourceFromRoot(path));
		h.setCookieStore(cookieStr);
		if (remoteMode == RemoteMode.遠端) {
			RMIClient.get();
		}
	}

	public void 讀取文章列表(String url) throws IOException {
		String result = h.cookiesHttp(url);// 讀取
		Document doc = Jsoup.parse(result);
		Elements es = doc.select(".gl3c a");
		for (Element e : es) {
			// System.out.println(e.outerHtml());// 這裡可以取得網扯和名字
			String nextUrl = e.attr("href");
			if (site == Site.e) {
				nextUrl = nextUrl.replaceAll(Pattern.quote(Site.e.toString()), Site.ex.toString());
			}

			String exid = nextUrl.split("/")[4];
			// String title = e.text();
			String title = e.select(".glink").get(0).text();
			HashMap map = new HashMap();
			map.put("exid", exid);
			map.put("title1", title);
			map.put("url", nextUrl);

			List<HashMap> list = SqlDao.get().撈取ex資料(map);
			if (list.size() > 0) {
				System.out.print("------已經存在了");
				System.out.println(map);
			} else {
				System.out.print("inserting");
				System.out.println(map);
				try {
					SqlDao.get().新增一筆ex資料(map);
				} catch (Exception ex) {

					map.put("downloaded", 0);
					map.put("looked", 0);
					System.out.println("updating");
					System.out.println(map);
					SqlDao.get().更新ex資料(map);
				}
			}
		}
	}

	public void 圖檔列表頁面(String nextUrl) throws Exception {
		if (count > stopCount) {
			System.out.println("stop count=" + count);
			throw new RuntimeException("超過了");
		}

		String result = "";
		for (int i = 0; i < 20; i++) {
			try {
				if (remoteMode == RemoteMode.遠端) {
					result = RMIClient.get().cookiesHttp(nextUrl);
				} else {
					result = h.cookiesHttp(nextUrl);
				}
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
			System.out.println("page = " + page + ",imgUrl=" + imgUrl);
			try {
				HashMap map = new HashMap();
				map.put("pageurl", imgUrl);
				map.put("exid", row.get("exid"));
				if (downloadMode == DownloadMode.接下去下載) {// 如果已有儲存下存的時候，就return false不載圖了
					List list = SqlDao.get().撈取excache資料(map);
					if (list.size() > 0) {
						System.out.println("發現cache記錄，跳過下載" + map);
						return;
					}
				}
				boolean saveOk = 圖檔頁(imgUrl, page);// 核心的下載程式
				if (saveOk && downloadMode == DownloadMode.接下去下載) {// 如果下載成功的話，新增一筆cache記錄
					SqlDao.get().新增一筆excache資料(map);
					System.out.println("新增了一筆cache記錄" + map);
				}
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

	/**
	 * 
	 * @param imgUrl
	 * @param page
	 * @param isUseCache
	 *            預設帶0，有發現cache會變成1，重作1次沒有快取帶2
	 * @return
	 * @throws Exception
	 */
	public boolean 圖檔頁(String imgUrl, String page) throws Exception {
		String exid = "" + row.get("exid");
		HashMap cacheMap = new HashMap();
		cacheMap.put("exid", exid);
		cacheMap.put("pageurl", imgUrl);
		String newPage = String.format("%05d", Integer.parseInt(page));
		String imgSrc = "";
		String info = "";
		String fail = "";

		if (StringUtils.isBlank(imgSrc)) {// 沒有從快取捉到要用的值，就從網頁去捉
			String html = 取得圖檔頁資訊(imgUrl, page);
			if (StringUtils.isBlank(html)) {// 捉不到圖檔頁的html，就無法執行，就跳開
				return false;
			}
			Document doc = Jsoup.parse(html);
			imgSrc = doc.select("#i3 img").attr("src");
			info = doc.select("#i4").text();
			fail = doc.select("#loadfail").attr("onclick");
			fail = StringUtils.substringBefore(StringUtils.substringAfter(fail, "'"), "'");
			System.out.println(imgSrc);
			System.out.println(info);
		}
		boolean saveFlag = 存圖檔(imgUrl, imgSrc, page, newPage, info, fail);
		return saveFlag;
	}

	/**
	 * 解析出圖檔頁的html來
	 * 
	 * @param imgUrl
	 * @param page
	 * @return
	 * @throws IOException
	 */
	public String 取得圖檔頁資訊(String imgUrl, String page) throws IOException {
		String result = null;
		for (int i = 0; i < 10; i++) {
			try {
				doThreadSleep();
				if (remoteMode == RemoteMode.遠端) {
					count++;
					result = RMIClient.get().cookiesHttp(imgUrl);
				} else {
					count++;
					result = h.cookiesHttp(imgUrl);
				}
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (StringUtils.isBlank(result)) {
			String newLog = outDir.getAbsolutePath() + "pageError_" + page + "_" + new Date().getTime() / 1000 % 3600
					+ ".txt";
			File newLogFile = new File(newLog);
			String log = "" + row.get("url") + "\r\n" + imgUrl;
			FileUtils.writeStringToFile(newLogFile, log);
		}
		return result;
	}

	/**
	 * 
	 * @param imgUrl
	 * @param imgSrc
	 * @param page
	 * @param newPage
	 * @param info
	 * @param fail
	 * @return
	 * @throws Exception
	 */
	public boolean 存圖檔(String imgUrl, String imgSrc, String page, String newPage, String info, String fail)
			throws Exception {

		// 存圖檔
		File outFile = 共用.checkFile(outDir.getAbsolutePath(),
				newPage + "_" + StringUtils.substringAfterLast(imgSrc, "/"), "");
		// File outFile = new File(outDir, StringUtils.substringAfterLast(imgSrc, "/"));
		try {
			HttpUtils.httpTry(imgSrc, outFile, 5);
		} catch (Exception ex) {
			if (imgUrl.indexOf("?nl=") < 0) {
				return 圖檔頁(imgUrl + "?nl=" + fail, page);
			}
			String newLog = outDir.getAbsolutePath() + "imgError_" + StringUtils.substringAfterLast(imgSrc, "/") + "_"
					+ new Date().getTime() / 1000 % 3600 + ".txt";
			File newLogFile = new File(newLog);
			String log = "" + row.get("url") + "\r\n" + info + "\r\n" + imgSrc + "\r\n" + outFile;
			FileUtils.writeStringToFile(newLogFile, log);
			return false;
		}
		return true;
	}

	public void doThreadSleep() throws Exception {
		while (true) {
			// System.out.println(Thread.currentThread().getId() + "-" + new
			// Date(System.currentTimeMillis()));
			synchronized (obj) {
				if (System.currentTimeMillis() - d.getTime() > 0) {
					d = new Date(System.currentTimeMillis() + 時間間隔 - 500 + rand.nextInt(1000));// 弄亂時間差
					System.out.println("下一次可執行時間" + Thread.currentThread().getId() + "-" + d);
					break;
				}
			}
			Thread.sleep(300 + rand.nextInt(300));
		}
		System.out.println("執行時間:" + Thread.currentThread().getId() + "-" + new Date(System.currentTimeMillis()));
	}
}
