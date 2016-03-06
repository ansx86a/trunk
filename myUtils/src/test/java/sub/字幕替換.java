package sub;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class 字幕替換 {

	public static void main(String args[]) throws Exception {
		URL location = 字幕替換.class.getProtectionDomain().getCodeSource().getLocation();
		Path p = Paths.get(location.toURI()).getParent();
		File utf8File = p.resolve("來源字幕").resolve("utf8").toFile();
		File utf16File = p.resolve("來源字幕").resolve("utf16").toFile();
		String newStyle = FileUtils.readFileToString(p.resolve("replace.txt").toFile(), "utf8");

		for (File f : utf8File.listFiles()) {
			String s = FileUtils.readFileToString(f, "utf8");
			輸出(s, newStyle, "utf8", p, f.getName());
		}

		for (File f : utf16File.listFiles()) {
			String s = FileUtils.readFileToString(f, "utf16");
			輸出(s, newStyle, "utf16", p, f.getName());
		}

		// File f = new File("G:/temp/1.ass");
		// String s = FileUtils.readFileToString(f, "utf8");
		// int start = StringUtils.indexOfIgnoreCase(s, "[v4");
		// int end = StringUtils.indexOfIgnoreCase(s, "[event");

		// String newStyle = "[v4 style] lllll lllll lllll";
		// // System.out.println(s.substring(start, end));
		// // System.out.println(s.substring(0,start));
		// // System.out.println(s.substring(end));
		//
		// String result = s.substring(0, start) + newStyle + "\r\n" +
		// s.substring(end);
		// System.out.println(result);
	}

	public static void 輸出(String s, String newStyle, String type, Path p, String fileName) throws Exception {
		int start = StringUtils.indexOfIgnoreCase(s, "[v4");
		int end = StringUtils.indexOfIgnoreCase(s, "[event");
		if (start < 0 || end < 0) {
			System.out.println("小於0   " + p + "/" + fileName + "-" + type);
			return;
		}

		String result = s.substring(0, start) + newStyle + "\r\n" + s.substring(end);
		File resutlFile = p.resolve("輸出字幕").resolve(type).resolve(fileName).toFile();
		resutlFile.getParentFile().mkdirs();
		FileUtils.writeStringToFile(resutlFile, result, type);
	}

}
