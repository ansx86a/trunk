package utils;

import java.io.File;
import java.net.URISyntaxException;

public class Utils {

	public static File getRootFile() {
		try {
			File f = new File(ClassLoader.getSystemResource("").toURI());
			return f;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static File getResourceFromRoot(String resource) {
		try {
			File f = getRootFile();
			f = new File(f,resource);
			return f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}