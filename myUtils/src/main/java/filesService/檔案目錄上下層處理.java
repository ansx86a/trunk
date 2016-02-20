package filesService;

import java.io.File;
import java.nio.file.Paths;

public class 檔案目錄上下層處理 {

	public static void main(String[] s) {
		檔案目錄上下層處理 a = new 檔案目錄上下層處理();
		String path = "F:\\BaiduYunDownload\\anime\\樱子小姐的脚下埋着尸体";
		a.往上移一層目錄controller(path);
	}

	public void 往上移一層目錄controller(String path) {
		File f = new File(path);
		if (f.getParentFile() == null) {
			System.out.println("error 在根目錄不能上移");
			return;
		}

		for (File subFile : f.listFiles()) {
			// 第一層目錄的檔案不用移，只要移目錄下的檔案的就好了
			if (subFile.isFile()) {
				continue;
			}
			往上移一層目錄(subFile);
			// 移完後目錄為空的話就砍掉
			if (subFile.listFiles().length == 0) {
				subFile.delete();
			}
		}

	}

	public void 往上移一層目錄(File f) {// f為基準點目錄，subFile主要是找到子檔案非目錄後，往上移一層
		for (File subFile : f.listFiles()) {
			File newFile = subFile.toPath().resolveSibling(subFile.getName()).toFile();
			// 避免相同的名稱向上移動
			if (!newFile.exists()) {
				subFile.renameTo(newFile);
			} else {
				System.out.println("已有上一層相同的名稱--" + newFile);
			}
		}
		System.out.println("結束往上移一層目錄");
	}

}
