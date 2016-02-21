import java.io.File;
import java.io.IOException;
import java.util.Date;

import filesService.目錄rename;

public class FileTest {

	public static void main(String[] args) throws IOException {

		String s1 = "G:\\temp\\456";
		String s2 = "G:\\temp\\123456";
		System.out.println(new Date());
		File f1 = new File(s1);
		File f2 = new File(s2);
		目錄rename.目錄搬移(f1, f2);
		System.out.println(new Date());

	}

}
