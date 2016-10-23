package 命列工具;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import hook.KeyHook建置器;
import utils.剪貼簿;

public class 刪除字幕廣告 {

	String[] deleteStr = new String[] { ",Warning,", ",banner;" };
	String encode = "utf16";

	public static void main(String[] args) {
		System.out.println("刪除字幕廣告 start");
		new 刪除字幕廣告().run();
		System.out.println("刪除字幕廣告 end");
	}

	public void run() {
		KeyHook建置器.加入一個hook(111, o -> {
			File cf = 剪貼簿.取得第一個檔案();
			if (cf != null && cf.exists() && cf.exists()) {
				System.out.println(cf);
				try {
					deleteSubLine(cf);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		});
	}

	public void deleteSubLine(File f) throws IOException {
		if (f.isFile()) {
			return;
		}
		for (File sub : f.listFiles()) {
			if (!sub.getName().endsWith(".ass")) {
				continue;
			}
			System.out.println("處理檔案：" + sub.getName());
			List<String> newList = new ArrayList<>();
			boolean writeFlag = false;
			for (String s : FileUtils.readLines(sub, encode)) {
				if (StringUtils.containsAny(s, deleteStr)) {
					writeFlag = true;
					continue;
				}
				newList.add(s);
			}
			System.out.println("writeFlag：" + writeFlag);
			if (writeFlag) {
				System.out.println(sub);
				FileUtils.writeLines(sub, newList);
			}
		}
	}
}
