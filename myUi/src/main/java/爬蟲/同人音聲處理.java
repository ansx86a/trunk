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
//		noCareList.add("RJ271520");
//		noCareList.add("RJ266742");
//		noCareList.add("RJ267630");
//		noCareList.add("RJ271867");
//		noCareList.add("RJ271908");
//		noCareList.add("RJ268425");
//		noCareList.add("RJ270913");
	
		//////////////////////////////////////////999999
//		noCareList.add("RJ262203");
//		noCareList.add("RJ262290");
//		noCareList.add("RJ264095");
//		noCareList.add("RJ265051");
//		noCareList.add("RJ265051");
//		noCareList.add("RJ265051");
//		noCareList.add("RJ265051");

		// noCareList.add("RJ130654");
		// noCareList.add("RJ227046");
		// noCareList.add("RJ218885");

		// 下面先跳過，晚點補回來
		// noCareList.add("RJ224621");

	}

	public static void main(String[] args) throws IOException {
		同人音聲處理 a = new 同人音聲處理();
		String zipDir = "G:\\01\\ok";
		String txtPath = "G:\\01\\download01.txt";
		// String zipDir = "H:\\micovoice\\201905\\ok";
		// String txtPath="E:\\googleDrive\\my\\download05.txt";

		a.檢查有無漏掉的檔案(new File(zipDir), new File(txtPath));

		// a.檢查有無漏掉的檔案(new
		// File("E:\\tool\\4網路網盤等工具\\MiPonyPortable\\Data\\downloads\\09"),
		// new File("D:\\voice\\201809\\download09.txt"));
		System.out.println("end");
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
