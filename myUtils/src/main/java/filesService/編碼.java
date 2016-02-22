package filesService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 2016/02/22整理，參照Supported Encodings<br>
 * https://docs.oracle.com/javase/7/docs/technotes/guides/intl/encoding.doc.html
 * https://docs.oracle.com/javase/1.5.0/docs/guide/intl/encoding.doc.html
 * 
 * @author ai
 *
 */
public class 編碼 {

	public static ArrayList<String> get全部編碼名稱() {
		// 有其中兩種編碼暫時有錯先去掉
		// ISO2022_CN_CNS,ISO2022_CN_GB,
		String all = "ISO8859_1,ISO8859_2,ISO8859_4,ISO8859_5,ISO8859_7,ISO8859_9,ISO8859_13,ISO8859_15,KOI8_R,ASCII,UTF8,UTF-16,UnicodeBigUnmarked,UnicodeLittleUnmarked,Cp1250,Cp1251,Cp1252,Cp1253,Cp1254,Cp1257,UnicodeBig,UnicodeLittle,Big5,Big5_HKSCS,EUC_JP,EUC_KR,GB18030,EUC_CN,GBK,Cp838,Cp858,Cp1140,Cp1141,Cp1142,Cp1143,Cp1144,Cp1145,Cp1146,Cp1147,Cp1148,Cp1149,Cp037,Cp1026,Cp1047,Cp273,Cp277,Cp278,Cp280,Cp284,Cp285,Cp297,Cp420,Cp424,Cp437,Cp500,Cp775,Cp850,Cp852,Cp855,Cp857,Cp860,Cp861,Cp862,Cp863,Cp864,Cp865,Cp866,Cp868,Cp869,Cp870,Cp871,Cp918,ISO2022CN,ISO2022JP,ISO2022KR,ISO8859_3,ISO8859_6,ISO8859_8,SJIS,TIS620,Cp1255,Cp1256,Cp1258,MS932,Big5_Solaris,EUC_JP_LINUX,EUC_TW,EUC_JP_Solaris,Cp1006,Cp1025,Cp1046,Cp1097,Cp1098,Cp1112,Cp1122,Cp1123,Cp1124,Cp1381,Cp1383,Cp33722,Cp737,Cp856,Cp874,Cp875,Cp921,Cp922,Cp930,Cp933,Cp935,Cp937,Cp939,Cp942,Cp942C,Cp943,Cp943C,Cp948,Cp949,Cp949C,Cp950,Cp964,Cp970,ISCII91,x-iso-8859-11,JISAutoDetect,x-Johab,MacArabic,MacCentralEurope,MacCroatian,MacCyrillic,MacDingbat,MacGreek,MacHebrew,MacIceland,MacRoman,MacRomania,MacSymbol,MacThai,MacTurkish,MacUkraine,MS950_HKSCS,MS936,PCK,MS874,MS949,MS950";
		ArrayList<String> list = new ArrayList<>();
		for (String s : all.split(",")) {
			list.add(s);
		}
		return list;
	}

	public static void main(String args[]) throws UnsupportedEncodingException {
		System.out.println(get全部編碼名稱());
		System.out.println(get全部編碼名稱().size());
		String a = "あいうえお";
		byte[] b = a.getBytes("big5");
		String c = new String(b, "ISO8859_1");// 錯誤編碼造成的自然語言錯誤
		System.out.println(c);
		System.out.println(new String(b, "big5"));// 正確的編碼正確

		// 再來嘗試用錯誤的編碼回推成正常的語言
		b = c.getBytes("ISO8859_1");
		System.out.println(new String(b, "big5"));
		c = "Æ¦Æ¨ÆªÆ¬Æ®";
		b = c.getBytes("ISO8859_1");
		System.out.println(new String(b, "big5"));

		結論_一層編碼錯誤: {
			// 1.先用本地碼回復byte編碼，應該是ms950或big5其中之一吧
			// 2.再用全部的編碼encode一輪應該就會回覆成自然語言了

		}
		System.out.println("======================");
		for (String e : get全部編碼名稱()) {
			System.out.println(new String(b, e));
		}

	}
}
