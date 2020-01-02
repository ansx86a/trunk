package 爬蟲;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

import regular.正規表示式取值;

public class Ex紳士ListError2 {

	TreeSet<Integer> set = new TreeSet<>();

	public static void main(String[] args) throws IOException {
		File dir = new File("G:\\moe\\");
		// File dir = new File("D:\\moe\\exwat");

		Ex紳士ListError2 e = new Ex紳士ListError2();
		e.$1產生exid列表(dir);
		System.out.println(e.set);

	}

	private void $1產生exid列表(File dir) {
		if (!dir.isDirectory()) {
			return;
		}
		String result = 正規表示式取值.取得第一個值("exid_[0-9]{4,10}\\)", dir.getName());
		if (result.endsWith(")")) {
			result = result.replaceAll("\\)", "");
			Integer item = Integer.parseInt(result.split("_")[1]);
			set.add(item);
			return;
		}
		for (File f : dir.listFiles()) {
			$1產生exid列表(f);
		}
	}

}
