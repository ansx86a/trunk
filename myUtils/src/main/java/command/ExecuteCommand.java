package command;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class ExecuteCommand {
	static String result = "";

	public static String exe(String cmd) {
		Process p;
		try {
			p = Runtime.getRuntime().exec(cmd);
			new Thread(new Runnable() {
				public void run() {
					try {
						result = IOUtils.toString(p.getInputStream());
						System.out.println("===========");
						System.out.println(result);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}).start();
			int r = p.waitFor();
			return "" + r + "\r\n" + result;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		//String result = exe("C:/Java/jdk1.8.0_45/bin/java.exe");
		//aaa.bat的內容就是個簡單的echo，可以印出整個commanLine的畫面無誤
		//像dir和java這一類的印不出來
		String result = exe("z:/aaa.bat");
		System.out.println(result);
	}

}
