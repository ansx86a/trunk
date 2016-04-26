package filesService;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class 檔案目錄Utils {

	public static void main(String[] args) {
		File f = new File("E:/tool");

		System.out.println(取得大小bytes(f) + "Bytes");
		System.out.println(取得大小kbytes(f) + "KB");
		System.out.println(取得大小mb(f) + "MB");
	}

	public static long 取得大小bytes(File f) {
		long size = FileUtils.sizeOfDirectory(f);
		return size;
	}

	public static long 取得大小kbytes(File f) {
		long size = FileUtils.sizeOfDirectory(f);
		return size / 1024;
	}

	public static long 取得大小mb(File f) {
		long size = FileUtils.sizeOfDirectory(f);
		return size / 1024 / 1024;
	}

}
