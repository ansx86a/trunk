package 爬蟲;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class 暗示部落格 {
	String root = "http://tomo2008.blog.shinobi.jp";

	public static void main(String[] args) throws IOException {
		// 1-27，空的2，3，5，7，8，9，22
		暗示部落格 a = new 暗示部落格();
		// a.runFor("射・管・屋 ( 78 )", "/Category/0");
		// a.runFor("暗示 ( 84 )", "/Category/10");
		// a.runFor("貞操帯管理 ( 3 )", "/Category/14");
		// a.runFor("フィクション小説 ( 68 )", "/Category/4");
		// a.runFor("ショートストーリー ( 31 )", "/Category/6");
		// a.runFor("音声化 ( 8 )", "/Category/13");
		// a.runFor("Chastity Program ( 61 )", "/Category/12");
		// a.runFor("その他 ( 661 )", "/Category/1");
		// a.runFor("非公開用 ( 5 )", "/Category/11");
		// a.runFor("おしらせ ( 10 )", "/Category/15");
		// a.runFor("M転記事 ( 18 )", "/Category/16");
		// a.runFor("女性向け記事 ( 38 )", "/Category/17");
		// a.runFor("オブジェ化 ( 23 )", "/Category/18");
		// a.runFor("直立不動 ( 6 )", "/Category/19");
		// a.runFor("彼氏がマゾ・・・ ( 12 )", "/Category/20");
		// a.runFor("PR -宣伝- ( 16 )", "/Category/21");
		// a.runFor("料理のレシピ ( 1 )", "/Category/23");
		// a.runFor("反芻 ( 19 )", "/Category/24");
		// a.runFor("貢ぎ奴隷 ( 19 )", "/Category/25");
		// a.runFor("シットイヌ ( 6 )", "/Category/26");
		// a.runFor("射精犬 ( 1 )", "/Category/27");

		System.out.println("end");
	}

	public void runFor(String dir, String url) throws IOException {
		String dir2 = dir.substring(dir.indexOf("(") + 1);
		dir2 = dir2.substring(0, dir2.indexOf(")")).trim();
		// 分頁是一頁25筆
		int number = Integer.parseInt(dir2);

		if (number == 1) {// 為了處理長度等於1不動作，加1不影嚮分頁處理
			number++;
		}
		for (int i = 1; i < number; i += 25) {
			String page = "";
			if (i > 25) {
				page = "/" + (1 + i / 25);
			}
			$1(dir, url + page);
		}
	}

	public void $1(String dir, String url) throws IOException {
		url = root + url;
		System.out.println(url);
		Document doc = Jsoup.connect(url).get();
		Elements es = null;
		es = doc.select("div.EntryInnerBlock");
		for (int i = 0; i < es.size(); i++) {
			Element e = es.get(i);
			String title = e.select("h2.EntryTitle").text();
			String time = e.select("li.EntryTime").text().replaceAll("/", "");
			System.out.println(title);
			String postUrl = root + e.select("p.EntryPsTitle a").attr("href");
			System.out.println(postUrl);
			String number = postUrl.replaceAll("http://tomo2008.blog.shinobi.jp/Entry/", "");
			System.out.println(number);
			number = number.replaceAll("/#ps_text", "");
			try {
				number = String.format("%04d", Integer.parseInt(number));
			} catch (Exception ex) {
				System.out.println("txt check " + title);
				FileUtils.writeStringToFile(new File("z:/" + dir + "/" + time + title + ".short.txt"), e.html());
				continue;
			}
			byte[] bs = IOUtils.toByteArray(new URL(postUrl));
			FileUtils.writeByteArrayToFile(new File("z:/" + dir + "/" + time + title + ".txt"), bs);
		}
	}
}
