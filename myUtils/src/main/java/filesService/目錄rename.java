package filesService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class 目錄rename {
	public static void main(String args[]) throws InterruptedException {
		String path = "F:\\BaiduYunDownload\\gmgard.us\\RJ129124\\yuri01_mp3 RJ129124";
		File f = new File(path);
		deleteBlankDir(f);
	}

	public static void 目錄搬移(File d1, File d2) throws InterruptedException {
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
		deleteBlankDir(d1);
	}

	/**
	 * 
	 * @param f
	 *            來源目錄
	 * @param pt
	 *            要從新命名的路徑(重新定位的root，相對來說是目標目錄)
	 * @param list
	 *            要從新命名的目錄路徑(root下去的，每遞迴一次多一個目錄，放string)
	 * @throws InterruptedException
	 */
	private static void renameFile(File f, Path pt, List<String> list) throws InterruptedException {
		// System.out.println(f.getAbsolutePath());
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
		}
	}

	private static void deleteBlankDir(File dir) throws InterruptedException {
		if (!dir.isDirectory()) {
			return;
		}
		File fs[] = dir.listFiles();
		if (fs.length == 0) {
			dir.delete();
			Thread.sleep(100);
			return;
		}
		for (File f : fs) {
			// 有檔案就先中斷，要全是目錄才是空目錄
			if (!f.isDirectory()) {
				return;
			}
			deleteBlankDir(f);
		}
		fs = dir.listFiles();
		if (fs.length > 0) {
			System.out.println("清空目錄錯誤=====================");
			return;
		} else {
			dir.delete();
			Thread.sleep(100);
		}

	}
}
