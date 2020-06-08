package dao;

import org.apache.commons.io.FileUtils;
import regular.正規表示式取值;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class Ex紳士ListError {

	TreeSet<Integer> set = new TreeSet<>();
	ArrayList<File> list = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		File dir = new File("D:\\moe\\ex");
		//File dir = new File("D:\\moe\\exwat");
		
		Ex紳士ListError e = new Ex紳士ListError();
		e.$1由exid去產生sql的where條件(dir);
		e.$2由set去刪除dir(dir);
	}

	private void $1由exid去產生sql的where條件(File dir) throws IOException {
		for (File f : dir.listFiles()) {
			if (f.getName().endsWith(".txt")) {
				// System.out.println(f.getName());
				String result = 正規表示式取值.取得第一個值("exid_[0-9]{4,10}\\)", f.getName());
				System.out.println(result);
				if (!result.endsWith(")")) {
					System.out.println(f.getName());
				} else {
					result = result.replaceAll("\\)", "");
					//f.delete();
				}
				this.set.add(Integer.parseInt(result.split("_")[1]));
			}
		}
		FileUtils.writeStringToFile(new File("z:/sql.txt"), this.set.toString());
		System.out.println(this.set.size());
	}

	public void $2由set去刪除dir(File dir) {
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				String result = 正規表示式取值.取得第一個值("exid_[0-9]{4,10}\\)", f.getName());
				if (!result.endsWith(")")) {
					continue;
				}
				result = result.replaceAll("\\)", "");
				Integer item = Integer.parseInt(result.split("_")[1]);
				System.out.println(item);

				if (this.set.contains(item)) {
					list.add(f);
				}
			}
		}

		System.out.println(list);
		System.out.println(list.size());
	}

}
