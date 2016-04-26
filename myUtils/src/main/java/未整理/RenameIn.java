package 未整理;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * <pre>
 * 這一個例子，要把他寫成UI才好用
 * UI可以keyin一個目錄，然後目錄會把File檔名列出來，用List去記
 * 最後輸出再改名字，而名子不包含路徑，只做一層的重新名命，當然目錄也不能重新命名
 * 最後輸出要確認筆數要一樣多才行，筆數不一樣多表示有不小心刪除到，不能重新名命
 * 筆數的有效判定暫定為可見字元3個字以上(名加點加副檔名)
 * </pre>
 * @author ai
 *
 */
public class RenameIn {

	private static String inPutTxt = "renameTxt.txt";

	public static void main(String[] args) throws Exception {
		// 測試用的參數
		// inPutTxt = "d:/test.txt";

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inPutTxt), "utf-8"));
		String line = null;
		while ((line = br.readLine()) != null) {
			if (line.indexOf("<=>") < 0)
				continue;
			String[] names = line.split("<=>");

			File nowFile = new File(names[1].trim());
			if (!nowFile.exists())
				continue;
			if (names[0].indexOf(65279) == 0) {
				names[0] = new String(names[0].toCharArray(), 1, names[0].toCharArray().length - 1);
			}

			File newFile = new File(nowFile.getParentFile().getCanonicalPath() + "/" + names[0].trim());
			if (!nowFile.getName().equals(newFile.getName())) {
				System.out.println(nowFile.getName() + " <=> " + newFile.getName());
				nowFile.renameTo(newFile);
			}
		}
		br.close();
	}
}
