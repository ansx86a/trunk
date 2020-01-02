package 爬蟲;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import regular.正規表示式取值;

public class 同人音聲處理 {

	static List<String> noCareList = new ArrayList<>();
	static {
//		noCareList.add("RJ235767");
		// noCareList.add("RJ222449");

		// 10
		// noCareList.add("RJ130654");

		// noCareList.add("RJ130654");
		// noCareList.add("RJ227046");
		// noCareList.add("RJ218885");

		// 下面先跳過，晚點補回來
		// noCareList.add("RJ224621");

	}

	public static void main(String[] args) throws IOException {
		同人音聲處理 a = new 同人音聲處理();
		String zipDir = "H:\\micovoice\\201906\\ok";
		String txtPath = "H:\\micovoice\\201906\\download06.txt";
		// String zipDir = "H:\\micovoice\\201905\\ok";
		// String txtPath="E:\\googleDrive\\my\\download05.txt";

		a.檢查有無漏掉的檔案(new File(zipDir), new File(txtPath));

		// a.檢查有無漏掉的檔案(new
		// File("E:\\tool\\4網路網盤等工具\\MiPonyPortable\\Data\\downloads\\09"),
		// new File("D:\\voice\\201809\\download09.txt"));

	}

	public void 檢查有無漏掉的檔案(File checkDir, File txtFile) throws IOException {
		if (!checkDir.exists() || !checkDir.isDirectory()) {
			throw new RuntimeException("錯誤的目錄");
		}
		List<String> fileList = Arrays.asList(checkDir.list());
		fileList.sort(String::compareTo);
		List<String> txtList = FileUtils.readLines(txtFile);

		// 先從file去檢查
		Label_File_Loop: for (String fileName : fileList) {
			String fileNameResult = 正規表示式取值.取得第一個值("RJ[0-9]{6}", fileName);
			System.out.println("check file" + fileName);
			if (StringUtils.isBlank(fileNameResult)) {
				throw new RuntimeException("沒有RJ號");
			}
			int j = 0;
			for (String line : txtList) {
				j++;
				String result = 正規表示式取值.取得第一個值("RJ[0-9]{6}", line);
				if (StringUtils.isBlank(result)) {
					continue;
				}
				if (result.equals(fileNameResult)) {
					// System.out.println("\t" + fileNameResult + "\t" + fileName);
					// System.out.print(j + "\t" + result + "\t");
					// System.out.println(line);
					continue Label_File_Loop;
				}
			}
			System.out.println("log:" + fileName);
			throw new RuntimeException("沒有在txt檔中找到");
		}

		int i = 0;
		Label_Txt_Loop: for (String line : txtList) {

			i++;
			// System.out.println(Arrays.toString(line.toCharArray()));
			// System.out.println(line.length());
			if (line.length() < 2 || StringUtils.startsWith(line, "http")) {
				continue;
			}
			System.out.println("check txt " + line);
			String result = 正規表示式取值.取得第一個值("RJ[0-9]{6}", line);
			if (StringUtils.isBlank(result)) {
				throw new RuntimeException("沒有RJ號");
			}

			if (noCareList.contains(result)) {
				continue Label_Txt_Loop;
			}

			for (String fileName : fileList) {
				String fileNameResult = 正規表示式取值.取得第一個值("RJ[0-9]{6}", fileName);

				if (result.equals(fileNameResult)) {
					// System.out.println("\t" + fileNameResult + "\t" + fileName);
					// System.out.print(i + "\t" + result + "\t");
					// System.out.println(line);
					continue Label_Txt_Loop;
				}
			}

			System.out.println("log:" + line);
			throw new RuntimeException("沒有在file中找到");
		}

	}

	public void 處理目錄(File checkDir, File txtFile) throws IOException {
		if (!checkDir.exists() || !checkDir.isDirectory()) {
			return;
		}
		List<String> fileList = Arrays.asList(checkDir.list());
		fileList.sort(String::compareTo);

		List<String> txtList = FileUtils.readLines(txtFile);
		// txtbase，列出沒有ok的列表
		int i = 0;
		for (String line : txtList) {
			i++;
			if (line.startsWith("ok")) {
				continue;
			}
			String result = 正規表示式取值.取得第一個值("RJ[0-9]{6}", line);
			if (StringUtils.isBlank(result)) {
				continue;
			}

			for (String fileName : fileList) {
				String fileNameResult = 正規表示式取值.取得第一個值("RJ[0-9]{6}", fileName);
				if (result.equals(fileNameResult)) {
					System.out.println("\t" + fileNameResult + "\t" + fileName);
					System.out.print(i + "\t" + result + "\t");
					System.out.println(line);
				}
			}
		}
		System.out.println("================================================================");
		// fileBase，列出file中有的列表
		for (String fileName : fileList) {
			String fileNameResult = 正規表示式取值.取得第一個值("RJ[0-9]{6}", fileName);
			if (StringUtils.isBlank(fileNameResult)) {
				continue;
			}
			int j = 0;
			for (String line : txtList) {
				j++;
				String result = 正規表示式取值.取得第一個值("RJ[0-9]{6}", line);
				if (StringUtils.isBlank(result)) {
					continue;
				}
				if (result.equals(fileNameResult)) {
					System.out.println("\t" + fileNameResult + "\t" + fileName);
					System.out.print(j + "\t" + result + "\t");
					System.out.println(line);
				}
			}

		}

	}
}
