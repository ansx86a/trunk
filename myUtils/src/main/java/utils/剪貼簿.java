package utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class 剪貼簿 {

	public static void main(String[] args) {
		// 放入文字("dddddd");
		// 放入檔案("G:\\temp\\111");

		System.out.println(取得複製的檔案列表());
		System.out.println(取得複製的檔案列表().get(0));
		System.out.println(取得複製的檔案列表().get(0).getClass());
	}

	public static String 取得複製的文字() {
		String result = "";
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = c.getContents(null);
		if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			try {
				result = (String) t.getTransferData(DataFlavor.stringFlavor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 這裡一開始以為是回傳文字列表，結果是檔案列表<br>
	 * 先都以文字列表考慮，以後再想要不要改回檔案列表
	 * @return
	 */
	public static List<String> 取得複製的檔案列表() {
		ArrayList<String> result = new ArrayList<>();
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = c.getContents(null);
		if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			try {
				for (File o : (List<File>) (t.getTransferData(DataFlavor.javaFileListFlavor))) {
					result.add(o.getAbsolutePath());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static File 取得第一個檔案() {
		List<String> list = 取得複製的檔案列表();
		if (list.size() > 0) {
			return new File(list.get(0));
		}
		return null;
	}

	public static void 放入文字(String s) {
		StringSelection ss = new StringSelection(s);
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		c.setContents(ss, null);
	}

	/**
	 * 未實作，可能自已實作一個Transferable就可以達到效果<br>
	 * 也有可能很麻煩要弄一個Stream寫資料進去，我想上面的機會比較大
	 * 
	 * @param s
	 */
	public static void 放入檔案(String s) {

		// Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		// try {
		// List<String> list = (List<String>)
		// c.getData(DataFlavor.javaFileListFlavor);
		// list.add(s);
		//
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }

	}

	/**
	 * 未實作
	 */
	public static void 放入圖片() {

		// Clipboard clipboard =
		// Toolkit.getDefaultToolkit().getSystemClipboard();
		// int n=0
		// for (DataFlavor flavor : flavors) {
		// BufferedImage image = (BufferedImage)clipboard.getData(flavor);
		// File file = new File("image"+ n+".png");
		// ImageIO.write(image , "png", file);
		// n++;
		// }

	}
}
