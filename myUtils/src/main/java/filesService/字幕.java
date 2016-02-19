package filesService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class 字幕 {

	private ArrayList<String> 字幕副檔名 = new ArrayList<String>();
	private ArrayList<String> 影片副檔名 = new ArrayList<String>();;

	private ArrayList<String> 拆分字 = new ArrayList<>();

	public 字幕() {
		字幕副檔名.add(".ass");
		字幕副檔名.add(".smi");
		字幕副檔名.add(".srt");

		影片副檔名.add(".avi");
		影片副檔名.add(".mkv");
		影片副檔名.add(".mp4");
		影片副檔名.add(".wmv");

		拆分字.add("[");
		拆分字.add("]");
		拆分字.add(" ");
		拆分字.add(".");
	}

	public static void main(String[] args) {
		字幕 sub = new 字幕();
		File f = new File("Z:\\test");
		sub.重新命名字幕檔(f);

	}

	public void 重新命名字幕檔(File f) {
		System.out.println("start rename");
		ArrayList<File> listMovie = new ArrayList<>();
		ArrayList<File> listSub = new ArrayList<>();
		// 用副檔名來拆開
		for (File subFile : f.listFiles()) {
			if (!subFile.isFile()) {
				continue;
			}
			if (StringUtils.endsWithAny(subFile.getName().toLowerCase(), 字幕副檔名.toArray(new String[] {}))) {
				listSub.add(subFile);
				continue;
			}
			if (StringUtils.endsWithAny(subFile.getName().toLowerCase(), 影片副檔名.toArray(new String[] {}))) {
				listMovie.add(subFile);
				continue;
			}
		}
		// 跑兩個迴圈去把相同數字的file找出來
		for (File mf : listMovie) {
			int mf_i = getNumber(mf.getName());
			for (File sf : listSub) {
				if (mf_i == getNumber(sf.getName())) {
					rename(mf, sf);
					break;
				}
			}
		}
		System.out.println("end rename");
	}

	public void rename(File mf, File sf) {
		String newName = mf.getName().split("\\.")[0];
		newName += sf.getName().substring(sf.getName().lastIndexOf("."));
		Path newPath = sf.toPath().resolveSibling("sub2").resolve(newName);
		try {
			FileUtils.copyFile(sf, newPath.toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNumber(String name) {
		for (int i = 1; i < 400; i++) {
			String ns = "" + i;
			if (ns.length() < 2) {
				ns = "0" + ns;
			}
			if (StringUtils.containsAny(name, getNumberTemplate(ns).toArray(new String[] {}))) {
				return i;
			}
			if (ns.length() < 3) {
				ns = "0" + ns;
			}
			if (StringUtils.containsAny(name, getNumberTemplate(ns).toArray(new String[] {}))) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * 硬取得數字的模組
	 * @param s
	 * @return
	 */
	public ArrayList<String> getNumberTemplate(String s) {
		ArrayList<String> template = new ArrayList<>();
		for (String s1 : 拆分字) {
			for (String s2 : 拆分字) {
				template.add(s1 + s + s2);
			}
		}
		return template;
	}
}
