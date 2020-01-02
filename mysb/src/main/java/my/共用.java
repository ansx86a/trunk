package my;

import java.io.File;

public class 共用 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * 處理window不合法的檔名成全型
	 * @param s
	 * @return
	 */
	public static String 處理檔名(String s) {
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
		s = s.trim();
		return s;
	}

	/**
	 * 確保不要檔名重覆去蓋到
	 * @param fileName
	 * @return
	 */
	public static File checkFile(String filePath, String fileName, String endStr) {
		if (fileName.length() > 75) {
			fileName = fileName.substring(0, 75).trim();
		}

		for (int i = 0; i <= 50; i++) {
			String append = i < 1 ? "" : String.format("%03d", i);// i=0時fileName不變，其它的時候帶i的值
			File f = new File(filePath, fileName + append + endStr);
			if (!f.exists()) {
				return f;
			}
		}
		return null;
	}

	public static File checkFile(String filePath, String fileName) {
		return checkFile(filePath, fileName, ".zip");
	}

}
