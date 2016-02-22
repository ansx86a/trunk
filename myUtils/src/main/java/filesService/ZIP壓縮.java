package filesService;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.lingala.zip4j.util.Zip4jUtil;

/**
 * 注意ZIP4J的一個特點，如果zip檔已存在，他不會把他zip蓋過去，而是在zip檔中加入此次的東西檔<br>
 * 感覺有vfs的概念一樣
 * @author ai
 *
 */
public class ZIP壓縮 {

	public static void 目錄壓ZIP(File f, boolean deleteAfterZip) throws Exception {
		if (f.isFile()) {
			System.out.println("不是目錄");
			new String();
			return;
		}
		// 再來是把FileList放進去
		ZipFile zipFile = new ZipFile(f.toPath().resolveSibling(f.getName() + ".zip").toFile());
		ZipParameters parameters = new ZipParameters();
		設壓縮率: {
			// 沒設好像就會預設成不壓縮
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			// parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
			// parameters.setCompressionMethod(Zip4jConstants.COMP_STORE);
		}
		設密碼: {
			// parameters.setEncryptFiles(true);
			// parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			// parameters.setPassword("1234");
		}
		ArrayList<File> fileList = new ArrayList<>();
		// 很貼心的設計
		for (File f2 : f.listFiles()) {
			if (f2.isFile()) {
				// zipFile.addFile(f2, parameters);
				fileList.add(f2);
			} else {
				// 這裡有取去File的方式，但是我不知道怎麼設到zip中的路徑去
				// ArrayList<File> l = Zip4jUtil.getFilesInDirectoryRec(f2, true);//
				// fileList.addAll(l);
				zipFile.addFolder(f2, parameters);
			}
		}

		if (fileList.size() > 0) {
			zipFile.addFiles(fileList, parameters);
		}

		if (deleteAfterZip) {
			FileUtils.deleteDirectory(f);
		}
	}

	public static void main(String args[]) throws Exception {
		File f = new File("Z:/zip4j_examples_1.3.2/Zip4jExamples");
		目錄壓ZIP(f, true);
		System.out.println("end");

	}

}
