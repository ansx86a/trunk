package 命列工具;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import hook.KeyHook建置器;
import utils.剪貼簿;

public class 刪除簡體字幕 {

	public static void main(String[] args) {
		new 刪除簡體字幕().run();
	}

	public void run() {
		KeyHook建置器.加入一個hook(111, o -> {
			File cf = 剪貼簿.取得第一個檔案();
			if (cf != null && cf.exists() && cf.exists()) {
				System.out.println(cf);
				deleteSub(cf);
			}
			return null;
		});
	}

	public void deleteSub(File f) {
		ArrayList<File> list = new ArrayList<>();
		Arrays.asList(f.listFiles()).forEach(o -> list.add(o));
		list.forEach(o -> {
			// 取出檔名
			String name = o.getName();
			if (!StringUtils.endsWithAny(name.toLowerCase(), "tc.ass", "tc.ssa")) {
				return;
			}
			// 去掉副檔名
			String name2 = StringUtils.substring(name, 0, name.length() - 6);
			// 選出開頭相同，但是結尾是sc.ass的字幕
			Optional<File> of = list.stream().filter(o2 -> StringUtils.startsWithIgnoreCase(o2.getName(), name2)
					&& StringUtils.endsWithAny(o2.getName().toLowerCase(), "sc.ass", "sc.ssa")).findFirst();
			// 取出sc.ass字幕並刪除
			if (of.isPresent()) {
				if (of.get().exists()) {
					System.out.println(of.get());
					of.get().delete();
				}
			}
		});

	}

}
