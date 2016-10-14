package 命列工具;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import hook.KeyHook建置器;
import utils.剪貼簿;

public class 字幕編碼轉換 {

	String oEnc = "utf16";
	String toEnc = "utf8";

	public static void main(String[] args) {
	
		System.out.println("字幕編碼轉換 start");
		new 字幕編碼轉換().run();
		System.out.println("字幕編碼轉換 end");
	}

	public void run() {
		KeyHook建置器.加入一個hook(111, o -> {
			File cf = 剪貼簿.取得第一個檔案();
			if (cf != null && cf.exists() && cf.exists()) {
				System.out.println(cf);
				try {
					toEnc(cf);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		});
	}
	
	
	

	public void toEnc(File f) throws IOException {
		if (f.isFile()) {
			return;
		}

		for (File sub : f.listFiles()) {
			if (!sub.getName().endsWith(".ass")) {
				continue;
			}
			String s = FileUtils.readFileToString(sub, oEnc);
			File newFile = f.toPath().resolve("bak").resolve(sub.getName()).toFile();
			FileUtils.write(newFile, s, toEnc);
		}
	}
}
