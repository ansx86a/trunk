import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class TestSub {

	public static void main(String[] args) throws IOException {
		File f = new File("G:/temp/1.ass");
		String s = FileUtils.readFileToString(f, "utf8");
		int start = StringUtils.indexOfIgnoreCase(s, "[v4");
		int end = StringUtils.indexOfIgnoreCase(s, "[event");

		String newStyle = "[v4 style] lllll lllll lllll";
		// System.out.println(s.substring(start, end));
		// System.out.println(s.substring(0,start));
		// System.out.println(s.substring(end));

		String result = s.substring(0, start) + newStyle + "\r\n" + s.substring(end);
		System.out.println(result);

	}

}
