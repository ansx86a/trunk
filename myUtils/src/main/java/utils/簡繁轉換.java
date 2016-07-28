package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.spreada.utils.chinese.ZHConverter;

public class 簡繁轉換 {

	public static void main(String[] args) throws IOException {

		// 應該有注意到，簡體轉繁體的時候有些字居然沒有改變，像是例子中的 "機" 轉不回來，
		// 簡體轉繁體會有一對多的問題，在此我稱為異體字，解決這問題可能需要一個對照表或是人工去判讀才可以

		String s = "有背光的機械式鍵盤にほんご";
		s = 繁轉簡(s);
		System.out.println(s);
		s = 簡轉繁(s);
		System.out.println(s);

		File f = new File("E:\\light\\no game no life");
		整個目錄轉換簡轉繁(f);
		System.out.println("end");
	}

	public static String 繁轉簡(String text) {
		// 繁體轉簡體
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
		String simplifiedStr = converter.convert(text);
		return simplifiedStr;
	}

	public static String 簡轉繁(String text) {
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.TRADITIONAL);
		String traditionalStr = converter.convert(text);
		return traditionalStr;
	}

	public static void 簡轉繁(File file) throws IOException {
		String str = FileUtils.readFileToString(file);
		str = 簡轉繁(str);
		FileUtils.writeStringToFile(file, str, "utf8");
	}

	public static void 整個目錄轉換簡轉繁(File dir) throws IOException {
		if (!dir.exists())
			return;
		if (dir.isFile())
			return;

		for (File f : dir.listFiles()) {
			if (!StringUtils.endsWithIgnoreCase(f.getName(), ".txt"))
				continue;
			String s = FileUtils.readFileToString(f);
			FileUtils.writeStringToFile(f, 簡轉繁(s));
		}
	}

}
