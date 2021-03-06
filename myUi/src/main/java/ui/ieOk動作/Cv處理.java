package ui.ieOk動作;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Cv處理 {
	private List<String> defaultCvList = new ArrayList<>();

	public static void main(String args[]) {
		Cv處理 cv處理 = new Cv處理();
		cv處理.addCv("justWeあいう什麼人會用");
	}

	public Cv處理() {
		InputStream i = ClassLoader.getSystemResourceAsStream("cvList.txt");
		try {
			String cvs = IOUtils.toString(i).replaceAll("\r\n", "");
			defaultCvList = Arrays.asList(cvs.split(","));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (defaultCvList.size() == 0) {
			defaultCvList = getCvList();
		}
	}

	public String getCvNames(String html) {
		String cv = "";
		for (String s : getCvList2()) {
			if (html.contains(s)) {
				cv += ("_" + s);
			}
		}
		return cv.replaceFirst("_", "");
	}

	public List<String> getCvList2() {
		File f = getCvFile();
		if (f == null || !f.exists()) {
			return defaultCvList;
		}
		try {
			String cvs = FileUtils.readFileToString(f).replaceAll("\r\n", "");
			List<String> cvList = Arrays.asList(cvs.split(","));
			if (cvList.size() > 0) {
				return cvList;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return defaultCvList;
	}

	public void addCv(String cv) {
		File cvFile = getCvFile();
		if (cvFile == null || !cvFile.exists()) {
			System.out.println("找不到cvFile");
			return;
		}
		if(getCvList2().contains(cv)){
			System.out.println("出現重覆的CV");
			return;
		}
		try {
			FileUtils.write(cvFile, "," + cv, true);
		} catch (Exception e) {
			e.printStackTrace();
	
		}
	}

	public File getCvFile() {
		ArrayList<String> result = new ArrayList<>();
		try {
			URI uri = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
			// file:/E:/workide/git_group/mygit/trunk/myUi/target/classes/
			File f = new File(uri).getParentFile().getParentFile();
			f = f.toPath().resolve("src").resolve("main").resolve("resources").resolve("cvList.txt").toFile();
			if (f.exists()) {
				return f;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getCvList() {
		StringBuffer cv = new StringBuffer();
		cv.append("紅月ことね,誠樹ふぁん,分倍河原シホ,彩瀬ゆり,伊ヶ崎綾香,紗藤ましろ,大山チロル");
		cv.append(",野上菜月,五十嵐ナナコ,綾奈まりあ,柚木朱莉,鹿乃仔,かの仔,伊東もえ,月宮怜,みる☆くるみ,天川みるく,椎那天");
		cv.append(",計名さや香,遠野そよぎ,北見六花");
		cv.append(",沢野ぽぷら,犬飼あお,さくら真咲,彩音まりか,秋葉よいこ");
		cv.append(",小粋,山田じぇみ子,美咲さゆり,浅見ゆい,涼貴涼");
		cv.append(",早乙女碧,いちる,白川パコ,星野ゆん,姫綺るいな");
		cv.append(",佐藤みるく,七凪るとろ,霜月優,桃華れん,古都にう,里村いのり,卯月杏奈,花見るん,柚木桃香");
		cv.append(",口谷亜夜,生田薫,織田リコ,野中みかん,かなせ,餅よもぎ,一之瀬りと");
		cv.append(",愛兎,蘇芳レア,加波美影,橘きの,淡雪,あさぎ夕羅,手塚りょうこ");
		cv.append(",久住わかば,東雲沙紗,藤村咲樹,渡瀬なゆか,西浦のどか,榊木春乃");
		cv.append(",カンザキカナリ,春日アン,ダイナマイト亜美,逢川奈々,蓮香,渋谷ひめ,岩泉まい,紫苑みやび");
		cv.append(",里々朱あん,猫実々美,天月琴子,東條みらの,秋葉モモ代,七海こねこ,紫月胡蝶,東めぐみ");
		cv.append(",瑠月未来,長月あいり,春河あかり,氷室百合,そらまめ,雁庵うずめ,藤堂れんげ");
		cv.append(",春乃ことり,藤川なつ,胡桃れみ,瑞希ましろ,榊山すず,歩サラ");
		cv.append(",あ、きのこ,金子未佳,若月Riko,真宮ひいろ,五十嵐斎,井上果林");
		cv.append(",三森愛乃,悠木藍香,藤邑鈴香,ニャルぽむぽむ");
		cv.append(",白鈴ももか,伊藤あすか,和鳴るせ,桃宮莉音,ゆきかすみ,渡辺恋詠,小日向さくら");
		cv.append(",白夜あこ,舞原ゆめ,糸井サラ,氷室玲奈,東十条真里亜");
		cv.append(",梨本悠里,小笠原麦,長瀬ゆずは,鈴谷まや,羽高なる,伊吹雫,癒祈亜,蜜嶺ゆりな");
		cv.append(",津軽りんご,浅倉小春,佐倉もも花,霧島はるな,渡会ななせ,輝月さくら");
		cv.append(",羽生りな,葉市憂,森野めぐむ,木下鈴,片倉ひな,小春日より,結野恵,大森ゆずり");
		cv.append(",めひこだぉ,伊東サラ,さやまひさこ,桃山小鳥,瓏暁露,藤野あい,秋川ひなた");
		cv.append(",木下いちご,美都柚希,星乃ありあ,香山いちご,愛東ななか,錯良紗玖");
		cv.append(",郷音いち,小鳥遊咲希,乃まりん,長坂光 ,高橋春香,羽月美叉,佐倉あり");
		cv.append(",戸川亜紀,きなりことみ,綿雪,和泉りゆ,藍沢夏癒,越乃あん,春名ことり");
		cv.append(",桐生まのあ,伊都あいな,壱那凛,チェ・ジル,紫藤かすみ,千秋りむ,滝元くるみ");
		cv.append(",蝉サブレ ,チン・グソク,水糸もか,藤倉ありさ,ヒマリ,秋野かえで");
		cv.append(",ゆづきひな,今谷皆美,蝉サブレ,御苑生メイ,このえゆずこ,花見川ゆふ,真中ゆき");
		cv.append(",しゅまりない,君野りるる,仁科真琴,冬宮あずき,柚凪,迩月弥生,酉乃結衣");
		cv.append(",水嶋せあ,逸見ゆうか,卯月にあ,長坂光,吉田幸代,髙木真綾,八劔すみれ");
		cv.append(",西澤香奈,かとう有花,吉部なる,夕凪音,樹ひなの,前野由佳子,早乙女花音");
		cv.append(",佐内小鳥,雨月紅羽,黒岡奈々緒,水瀬由菜,小日向るり,戸川亜樹,北板利亜,藍月なくる");
		Arrays.asList(new String[] { "1", "2" });
		return Arrays.asList(cv.toString().split(","));
	}
}
