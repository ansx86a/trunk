package command;

import java.io.File;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ProcessDestroyer;
import org.apache.commons.lang3.time.DateUtils;

/**
 * <pre>
 * 這支程式主要是處理一個非同步的run
 * 然後時間一到自已關掉
 * </pre>
 * @author ai
 *
 */
public class 固定時間執行後又關閉程式 {

	private Process ps;
	private String command;
	private long resetTime = 5 * DateUtils.MILLIS_PER_MINUTE;

	public static void main(String[] args) throws Exception {
		while (true) {
			固定時間執行後又關閉程式 a = new 固定時間執行後又關閉程式();
			File f = new File(ClassLoader.getSystemResource("command/123.txt").toURI());
			String line = "notepad.exe ";
			line += f.getAbsolutePath();
			
			line ="C:\\Program Files (x86)\\Thunder Network\\Thunder\\Program\\Thunder.exe";
			a.runAlways(line);
		}
	}

	public void runAlways(String line) throws Exception {
		while (true) {
			run(line);
			System.out.println("start wait" + resetTime / 1000 + "sec");
			Thread.sleep(resetTime);
			ps.destroy();
			System.out.println("==wait 3sec to restart");
			Thread.sleep(3000);
			System.out.println("=kill exec end===");
		}
	}

	public void run(String line) throws Exception {
		DefaultExecuteResultHandler rh = new DefaultExecuteResultHandler();
		CommandLine cmdLine = CommandLine.parse(line);
		DefaultExecutor exe = new DefaultExecutor();
		exe.setProcessDestroyer(new ProcessDestroyer() {
			@Override
			public int size() {
				return 0;
			}

			@Override
			public boolean remove(Process process) {
				System.out.println("remove" + process);
				ps = null;
				return false;
			}

			@Override
			public boolean add(Process process) {
				System.out.println("add" + process);
				ps = process;
				return false;
			}
		});
		exe.execute(cmdLine, rh);
	}

	public Process getPs() {
		return ps;
	}

	public void setPs(Process ps) {
		this.ps = ps;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public long getResetTime() {
		return resetTime;
	}

	public void setResetTime(long resetTime) {
		this.resetTime = resetTime;
	}

}
