package filesService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class 目錄rename {

	public static void 目錄搬移(File d1, File d2) {
		if (d1.isFile() || d2.isFile()) {
			System.out.println("不是目錄");
			return;
		}
		if (d2.getAbsolutePath().startsWith(d1.getAbsolutePath())) {
			System.out.println("新目錄不能在源目錄裡面");
			return;
		}

		for (File f : d1.listFiles()) {
			renameFile(f, d2.toPath(), new ArrayList<String>());
		}
		if (d1.isDirectory() && d1.listFiles().length == 0) {
			d1.delete();
		}
	}

	private static void renameFile(File f, Path pt, List<String> list) {
		//System.out.println(f.getAbsolutePath());
		if (f.isFile()) {
			Path p2 = Paths.get(pt.toString(), list.toArray(new String[] {}));
			p2 = p2.resolve(f.getName());
			if (!p2.toFile().getParentFile().exists()) {
				p2.toFile().getParentFile().mkdirs();
			}
			f.renameTo(p2.toFile());
		}
		if (f.isDirectory()) {
			for (File subF : f.listFiles()) {
				ArrayList<String> l = new ArrayList<>(list);
				l.add(f.getName());
				renameFile(subF, pt, l);
			}
			// 沒目錄沒東西時自已清空
			if (f.listFiles().length == 0) {
				f.delete();
			}
		}
	}

}
