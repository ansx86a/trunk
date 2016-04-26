package filesService;

import java.io.File;

import utils.Utils;

public class Rar在window的範例 {

	private String rarCmd = "C:\\Program Files\\WinRAR\\Rar.exe a ";
	private String unRarCmd = "C:\\Program Files\\WinRAR\\UnRar x ";

	// 此例的問題是壓出來的rar檔裡面的路徑會很多層，不知導怎麼減少層數
	// 此例先當娛樂用，真的要實作就要變成bat檔後切換目錄執行？？
	// zip4j的例子已經運行得不錯，壓縮建議直接用zip4j就好了
	public static void main(String args[]) {
		// 這裡的重點是在壓rar檔
		// 壓rar檔當成是娛樂吧

		// 要壓的來源路徑
		File sourceFile = Utils.getResourceFromRoot("filesService/testRar.txt");
		// rar的真實路徑
		File tFile = Utils.getResourceFromRoot("filesService/testRar.rar");
		Rar在window的範例 r = new Rar在window的範例();
		r.rar(sourceFile, tFile);
		// rar的解壓路徑
		File tFile2 = Utils.getResourceFromRoot("filesService/unrar解壓目錄");
		r.unRar(tFile, tFile2);
		System.out.println("done");
	}

	public void rar(File sf, File tf) {
		String cmd = rarCmd + " " + tf.getAbsolutePath() + " " + sf.getAbsoluteFile();
		System.out.println(cmd);
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unRar(File sf, File tf) {
		if (tf.isFile()) {
			return;
		}
		if (!tf.exists()) {
			tf.mkdirs();
		}
		String cmd = unRarCmd + " " + sf.getAbsolutePath() + " " + tf.getAbsoluteFile();
		System.out.println(cmd);
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
