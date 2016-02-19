package f;

import java.io.File;
import java.nio.file.Paths;

public class 檔案處理 {

	public static void main(String[] s) {
		檔案處理 a = new 檔案處理();
		a.往上移一層目錄();
	}

	public void 往上移一層目錄() {
		String path = "F:\\BaiduYunDownload\\anime\\樱子小姐的脚下埋着尸体";
		File f = new File(path);
		for (File subFile : f.listFiles()) {
			// 第一層目錄不用移，只要移目錄結構的就好了
			if (subFile.isFile()) {
				continue;
			}
			往上移一層目錄(subFile);
		}

	}

	public void 往上移一層目錄(File f) {// f為基準點目錄，subFile主要是找到子檔案非目錄後，往上移一層
		for (File subFile : f.listFiles()) {
			if (subFile.isDirectory()) {
				往上移一層目錄(subFile.getParentFile());
				// 目錄本身不用移上一層移動，只要移動檔案就好了
				continue;
			}
			File f3 = Paths.get(f.getParentFile().getAbsolutePath(), subFile.getName()).toFile();
			System.out.println(subFile.getAbsolutePath() + "->" + f3.getAbsolutePath());
			subFile.renameTo(f3);
			// File f3 = new File(subFile.getParentFile().gt)
		}
		System.out.println("結束往上移一層目錄");
	}

}
