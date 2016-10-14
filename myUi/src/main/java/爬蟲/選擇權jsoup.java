package 爬蟲;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class 選擇權jsoup {

	public static void main(String[] args) throws IOException, URISyntaxException {
		// TODO Auto-generated method stub
		選擇權jsoup j = new 選擇權jsoup();
		// j.$1();

		j.$30日期貨csv檔下載("2016", "10");
		j.$30日期貨價差csv檔下載("2016", "10");
		j.$30日選擇權csv檔下載("2016", "10");
		System.out.println("end");
	}

	public void $1() throws IOException {
		// 期貨
		String url = "http://info512.taifex.com.tw/Future/FusaQuote_Norl.aspx";
		Document doc = Jsoup.connect(url).get();
		Elements es = null;
		es = doc.select("tr.custDataGridRow>td:contains(商品)");
		es.forEach(o -> System.out.println(o.parent().text()));

		es = doc.select("tr>td.bu13:contains(小臺指期)");
		es.forEach(o -> System.out.println(o.parent().text()));

		url = "http://info512.taifex.com.tw/Future/OptQuote_Norl.aspx";
		doc = Jsoup.connect(url).get();

		es = doc.select("span[id=ctl00_ContentPlaceHolder1_lblWPrice]");
		String 大盤指數 = es.get(0).text();
		System.out.println("大盤指數:" + 大盤指數);

		es = doc.select("tr.custDataGridRow>td:contains(履約價)");
		es = es.get(0).parent().parent().getElementsByTag("tr");
		es.forEach(o -> System.out.println(o.text()));
	}

	public void $30日期貨csv檔下載(String year, String mon) throws IOException, URISyntaxException {
		// String url = "http://www.taifex.com.tw/chinese/3/3_1_3.asp";
		// ../../DailyDownload/DailyDownloadCSV/Daily_2016_09_19.zip
		String url = "http://www.taifex.com.tw/DailyDownload/DailyDownloadCSV/";
		String pre = "Daily_" + year + "_" + mon + "_%02d.zip";
		for (int i = 1; i < 32; i++) {
			String s = pre.format(pre, i);
			System.out.println(url + s);
			byte[] bs = IOUtils.toByteArray(new URI(url + s));
			System.out.println(bs.length);
			if (bs.length > 1000) {// 大於1k的長度
				FileUtils.writeByteArrayToFile(new File("z:/stock/期貨/" + s), bs);
			}
		}
	}

	public void $30日期貨價差csv檔下載(String year, String mon) throws IOException, URISyntaxException {
		// String url = "http://www.taifex.com.tw/chinese/3/3_1_5.asp";
		// ../../DailyDownload/DailyDownloadCSV_C/Daily_2016_09_19_C.zip

		String url = "http://www.taifex.com.tw/DailyDownload/DailyDownloadCSV_C/";
		String pre = "Daily_" + year + "_" + mon + "_%02d_C.zip";
		for (int i = 1; i < 32; i++) {
			String s = pre.format(pre, i);
			System.out.println(url + s);
			byte[] bs = IOUtils.toByteArray(new URI(url + s));
			System.out.println(bs.length);
			if (bs.length > 1000) {// 大於1k的長度
				FileUtils.writeByteArrayToFile(new File("z:/stock/期貨價差/" + s), bs);
			}
		}
	}

	public void $30日選擇權csv檔下載(String year, String mon) throws IOException, URISyntaxException {
		// String url = "http://www.taifex.com.tw/chinese/3/3_2_4.asp";
		// ../../DailyDownload/OptionsDailyDownloadCSV/OptionsDaily_2016_09_19.zip
		String url = "http://www.taifex.com.tw/DailyDownload/OptionsDailyDownloadCSV/";
		String pre = "OptionsDaily_" + year + "_" + mon + "_%02d.zip";
		for (int i = 1; i < 32; i++) {
			String s = pre.format(pre, i);
			System.out.println(url + s);
			byte[] bs = IOUtils.toByteArray(new URI(url + s));
			System.out.println(bs.length);
			if (bs.length > 1000) {// 大於1k的長度
				FileUtils.writeByteArrayToFile(new File("z:/stock/選擇權/" + s), bs);
			}
		}
	}

	/**
	 * 主要看成交明細的本日量價變化圖
	 */
	public static void kimo當日明細() {
		String url = "https://tw.stock.yahoo.com/q/ts?s=6532";
		// 0926 ，定價24，(28,410)(29.5,113)(31,185)，最低27.4(257)
		// 今日最高點24*1.3=31.2左右，最高的量大，加上申購價最大量28，剛好停在中間值29.5，是偶然嗎？
		//下次開盤日跌到27.4以下賣，收28.8以下賣，3日低點為27.4
		
		//今日低點支撐(30.20,248)，最低29.5，開盤(31,167)，收33.3
		//重點價位(30.5,99)大約是早盤的均價，(32,111)拉抬的突破點，(33.5,96)高點平均價
		//下次收盤破30.5就賣

	}

}