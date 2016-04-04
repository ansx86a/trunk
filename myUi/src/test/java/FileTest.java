import java.io.File;
import java.nio.file.Path;

public class FileTest {

	public static void main(String[] args) throws Exception {
		File f = new File("G:\\voiceMany\\ero hypno voice\\0Dream\\(同人ソフト)  [RJ065023]");
		System.out.println(f);
		Path p = f.toPath();
		System.out.println(p);
		Path p2=p.resolveSibling("zzz");
		System.out.println(p2);
		System.out.println(p2);

	}

}
