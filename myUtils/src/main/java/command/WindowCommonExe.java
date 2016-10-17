package command;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;

public class WindowCommonExe {

	public static void main(String[] args) {

	}

	/**
	 * <pre>
	 * 使用windows本身的命令，首先要关闭休眠，否则只能进入休眠状态
	 * powercfg -hibernate off
	 * 然后如下命令睡眠
	 * Rundll32.exe Powrprof.dll,SetSuspendState Sleep
	 * </pre>
	 * @throws Exception
	 */
	public static void 睡眠或休眠() throws Exception {
		run("Rundll32.exe Powrprof.dll,SetSuspendState Sleep");
	}

	/**
	 * <pre>
	 * psshutdown.exe -d -t 0
	 * 待機的意思，t後面接秒數
	 * </pre>
	 * @throws Exception
	 */
	public static void 睡眠() throws Exception {
		run("D:\\PSTools\\psshutdown.exe -d -t 0");
	}

	/**
	 * <pre>
	 * psshutdown.exe -h -t 0
	 * hibernate的意思，t後面接秒數
	 * </pre>
	 * @throws Exception
	 */
	public static void 休眠() throws Exception {
		run("D:\\PSTools\\psshutdown.exe -h -t 0");
	}

	public static void run(String line) throws Exception {
		DefaultExecuteResultHandler rh = new DefaultExecuteResultHandler();
		CommandLine cmdLine = CommandLine.parse(line);
		DefaultExecutor exe = new DefaultExecutor();
		exe.execute(cmdLine, rh);
	}
}
