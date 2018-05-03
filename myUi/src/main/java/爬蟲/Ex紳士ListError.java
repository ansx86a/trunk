package 爬蟲;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Ex紳士ListError {

	public static void main(String[] args) throws IOException {
		HashSet<String> set = new HashSet<>();

		File dir = new File("z:/1");
		System.out.println(dir.listFiles().length);
		for(File f:dir.listFiles()){
			List<String> list =		FileUtils.readLines(f);
			set.addAll(list);
		}
		System.out.println(set.size());
		System.out.println(set);
		System.out.println("where ");
		set.stream().forEach(o->System.out.println(",\'"+o+"\'"));

		
		
		
		
	}

}
