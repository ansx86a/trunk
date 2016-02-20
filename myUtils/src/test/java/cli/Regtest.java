package cli;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regtest {

	public static void main(String ar[]) {
		String rs = "[rR][jJ][0-9]{2,6}";
		Pattern p = Pattern.compile(rs);
		String a = "dddRJ143215ddRJ143216dddRj143217dddd";
		Matcher m = p.matcher(a);
		
		System.out.println(a.matches(rs));
		System.out.println(m.matches());

		// replaceAll不會發生上述的問題
		System.out.println(a.replaceAll(rs, "xxx"));
		System.out.println(m.find());
		System.out.println(m.find());
		System.out.println(m.find());
		System.out.println(m.find());
		System.out.println(m.group());
		
		printMatched(rs, a);
	}

	public static void printMatched(String regex, String source) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				System.out.println(m.group(i)+"-"+m.group());
			}
		}
	}

}
