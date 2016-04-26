package 未整理;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 同RenameIn.java
 * @author ai
 *
 */
public class RenameOut {

	private static String path = "path.txt";
	private static String outPutTxt = "renameTxt.txt";

	private ArrayList<File> fileList = new ArrayList<File>();

	private int[] size = { 1000, 100 };
	private char spaceChar = ' ';
	private int maxSize = 1;

	// 直接列表算了，最後再用絕對路徑排序就好了
	public void run(String path) throws Exception {
		File file = new File(path);
		System.out.println(file.exists());
		setDirFile(file);
		// 照字串的自然比較法排序
		java.util.Collections.sort(fileList, new Comparator<File>() {
			public int compare(File o1, File o2) {
				String path1 = "";
				String path2 = "";
				try {
					path1 = o1.getCanonicalPath();
					path2 = o2.getCanonicalPath();
				} catch (IOException e) {
				}
				return path1.compareTo(path2);
			}
		});
		writeOut();
	}
	public void setDirFile(File file) {
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				setDirFile(f);
			}
			if (f.isFile()) {
				fileList.add(f);
			}
		}
	}
	// 在寫出之前要先準好空白字元的長度
	public void writeOut() throws Exception {
		char[] chars1 = new char[size[0]];// 長度給它很長就對了
		Arrays.fill(chars1, spaceChar);
		ArrayList<String[]> outList = new ArrayList<String[]>();
		for (File f : fileList) {
			int ch2bytes = f.getName().replaceAll("\\p{ASCII}", "").length();
			int fSize = f.getName().length() + ch2bytes;
			if (maxSize <= fSize)
				maxSize = fSize;
			String[] out = new String[4];
			out[0] = (f.getName() + new String(chars1));
			out[1] = f.getCanonicalPath();
			outList.add(out);
		}
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outPutTxt), "utf-8"));
		for (String[] s : outList) {
			String outStr = mySub(s[0], maxSize + 10) + " <=> " + s[1];
			w.write(outStr + "\r\n");
		}
		w.flush();
		w.close();
	}

	public String mySub(String str, int size) {
		// 先算出佔兩個字元的字有幾個，再從size裡面減掉
		int ch2bytes = str.replaceAll("\\p{ASCII}", "").length();
		return str.substring(0, size - ch2bytes);
	}

	public static void main(String[] args) throws Exception {
		//以後要包成pakepage的話，把getPath寫成一個method吧
		
		String pathFileString = "path.txt";
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(pathFileString), "utf-8"));
		path = br.readLine().trim();
		
		//支援utf-8的話，文字檔的不可見字元的字串編碼的第一個char好像是在宣告utf-8的樣子，要把它拿掉就好了
		//註：好像是用記筆本才會這樣子，用其它的如pspPad好像不會
		//一般用的big5就不用刪了
		if(path.indexOf(65279)==0){
			path = new String(path.toCharArray(), 1, path.toCharArray().length - 1);  
		}

		// 測試的參數
		//path = "D:/test";
		//outPutTxt = "d:/test.txt";
		new RenameOut().run(path);
	}

}
