package cli;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * 撰寫commandLine的時候，因為要處理傳進來的參數很難做<br>
 * 就用Cli來幫助取值，另外也可以gen出help畫面
 * @author ai
 */
public class ApacheCliTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String myArgs = "     -u       user大爺   --password     \"123什麼鬼 東 西\"       -b";
		args = myArgs.split("[ ]+");
		System.out.println(Arrays.toString(args));
		Options options = new Options();
		// options.addOption(選項名稱, 選項別名, 是否帶參數, 選項描述);
		options.addOption("u", "username", true, "沒打 username");
		options.addOption("p", "password", true, "沒打 password");
		options.addOption("b", "background", false, "run in the background.");

		HelpFormatter formatter = new HelpFormatter();
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = null;
		String username = "";
		String password = "";
		boolean isBackground = false;
		try {
			cmd = parser.parse(options, args);
			username = cmd.getOptionValue("u", "");
			password = cmd.getOptionValue("p", "");
			isBackground = cmd.hasOption("b");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			formatter.printHelp("Test.jar", options);
			return;
		}

		if (username.isEmpty() || password.isEmpty()) {
			formatter.printHelp("Test.jar", options);
			return;
		}

		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Background: " + isBackground);

	}

}