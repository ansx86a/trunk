package regular;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 要注意的是match要先執行find()才能用(類似走訪)<br>
 * 走訪後就會捉到一個group，因為pattern可以對到多組，而表示式會取較大的值，大至上是這樣，細節以後再說
 * 
 * @author rockx
 *
 */
public class 正規表示式取值 {

	public static String 取得第一個值(String regex, String source) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);
		if (m.find()) {
			return m.group();
		}
		return "";
	}

	public static ArrayList<String> 取得全部值(String regex, String source) {
		ArrayList<String> list = new ArrayList<>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);
		while (m.find()) {
			list.add(m.group());
			for (int i = 0; i <= m.groupCount(); i++) {
				// System.out.println(m.group(i));
			}
		}
		return list;

	}

}
