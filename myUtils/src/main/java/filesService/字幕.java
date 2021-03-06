package filesService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import utils.簡繁轉換;

public class 字幕 {

	private ArrayList<String> 字幕副檔名 = new ArrayList<String>();
	private ArrayList<String> 影片副檔名 = new ArrayList<String>();;

	private ArrayList<String> 拆分字 = new ArrayList<>();

	public 字幕() {
		字幕副檔名.add(".ass");
		字幕副檔名.add(".ssa");
		字幕副檔名.add(".saa");
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
		拆分字.add("第");
		拆分字.add("話");
	}

	public static void main(String[] args) throws IOException {
		字幕 sub = new 字幕();
		File f = new File("Z:\\test");
		sub.重新命名字幕檔(f);

	}

	public void 重新命名字幕檔(File f) throws IOException {
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
				簡繁轉換.簡轉繁(subFile);
				continue;
			}
			if (StringUtils.endsWithAny(subFile.getName().toLowerCase(), 影片副檔名.toArray(new String[] {}))) {
				listMovie.add(subFile);
				continue;
			}
		}
		if (listSub.size() == 0) {
			System.out.println("sub files size == 0 to return");
			return;
		}
		File bakDir = new File(listSub.get(0).getParent(), "baksub" + new Date().getTime());
		bakDir.mkdirs();
		System.out.println("bakdir=" + bakDir);
		// 跑兩個迴圈去把相同數字的file找出來
		for (File mf : listMovie) {
			int mf_i = getNumber(mf.getName());
			for (File sf : listSub.toArray(new File[] {})) {
				if (mf_i == getNumber(sf.getName())) {
					listSub.remove(sf);
					File bakFile = new File(bakDir, sf.getName());
					System.out.println("bakFile=" + bakFile);
					if (sf.getAbsolutePath().equals(mf.getAbsolutePath())) {
						//本來字幕就是要的名字，同名，就copyfile
						FileUtils.copyFile(mf, bakFile);
					} else {
						//重新命名字幕並移到
						rename(mf, sf);
						sf.renameTo(bakFile);
					}
					break;
				}
			}
		}
		System.out.println("end rename");
	}

	public void rename(File mf, File sf) {
		String newName = mf.getName().split("\\.")[0];
		newName += sf.getName().substring(sf.getName().lastIndexOf("."));
		// Path newPath = sf.toPath().resolveSibling("sub2").resolve(newName);
		Path newPath = sf.toPath().resolveSibling(newName);
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

	public ArrayList<String> get拆分字() {
		return 拆分字;
	}

	public void set拆分字(ArrayList<String> 拆分字) {
		this.拆分字 = 拆分字;
	}

}
