package command;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.ProcessDestroyer;
import org.apache.commons.exec.PumpStreamHandler;

public class ApacheCommonExec {

	Process willCloseProcess;

	public static void main(String[] args) throws Exception {
		// 預設是同步
		ApacheCommonExec a = new ApacheCommonExec();
		a.$7控制一個exe的開始和kill();
	}

	public void $1最簡單的exec同步並sysout結果() throws Exception {
		String line = "ipconfig";
		CommandLine cmdLine = CommandLine.parse(line);
		DefaultExecutor exe = new DefaultExecutor();
		int exitValue = exe.execute(cmdLine);
		System.out.println(exitValue);
	}

	/**
	 * 這裡證明這是同步的程式，會等到exitValue傳回來後再跑下面的程式
	 * @throws Exception
	 */
	public void $2最簡單的帶入參數的exec() throws Exception {
		File f = new File(ClassLoader.getSystemResource("執行/123.txt").toURI());
		String line = "notepad.exe";
		CommandLine cmdLine = CommandLine.parse(line);
		cmdLine.addArgument(f.getAbsolutePath());
		DefaultExecutor exe = new DefaultExecutor();
		// 如果沒有exit value的話，可以透過這個來設定
		// exe.setExitValue(1);
		int exitValue = exe.execute(cmdLine);
		System.out.println(exitValue);
	}

	/**
	 * 把result 讀到一個stream裡面去，最後可以使用
	 * @throws Exception
	 */
	public void $3截取sysout的exec() throws Exception {
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		PumpStreamHandler psh = new PumpStreamHandler(stdout);
		// CommandLine cl = CommandLine.parse("ipconfig");
		// 本目錄
		// CommandLine cl = CommandLine.parse("cmd /c dir");
		// 其它目錄1
		// CommandLine cl = CommandLine.parse("cmd /c dir  d:\\ /A /Q");
		// 其它目錄2
		CommandLine cl = CommandLine.parse("cmd /c dir  d:\\");
		DefaultExecutor exec = new DefaultExecutor();
		exec.setStreamHandler(psh);
		int exitValue = exec.execute(cl);
		System.out.println(exitValue);
		System.out.println("===========");
		System.out.println(stdout.toString("ms950"));
	}

	/**
	 * 同範例3，但是使用buffer，放到line去，但是有編碼問題，要使用的話要重寫程式碼<br>
	 * 讓lines可以適應編碼
	 * @throws Exception
	 */
	public void $4截取sysout的exec2() throws Exception {
		CollectingLogOutputStream stdout = new CollectingLogOutputStream();
		PumpStreamHandler psh = new PumpStreamHandler(stdout);
		CommandLine cl = CommandLine.parse("ipconfig");
		DefaultExecutor exec = new DefaultExecutor();
		exec.setStreamHandler(psh);
		int exitValue = exec.execute(cl);
		System.out.println(exitValue);
		System.out.println("===========");
		System.out.println(stdout.getLines());
	}

	/**
	 * 測試非同步
	 * @throws Exception
	 */
	public void $5非同步的exec() throws Exception {
		DefaultExecuteResultHandler rh = new DefaultExecuteResultHandler();
		File f = new File(ClassLoader.getSystemResource("執行/123.txt").toURI());
		String line = "notepad.exe";
		CommandLine cmdLine = CommandLine.parse(line);
		cmdLine.addArgument(f.getAbsolutePath());
		DefaultExecutor exe = new DefaultExecutor();
		exe.execute(cmdLine, rh);
		System.out.println("==end=========");
	}

	/**
	 * 可以由工作管理員去關會有exception
	 * @throws Exception
	 */
	public void $6非同步exec且實作成功和失敗處理() throws Exception {
		ExecuteResultHandler rh = new MyResultHandler();
		File f = new File(ClassLoader.getSystemResource("執行/123.txt").toURI());
		String line = "notepad.exe";
		CommandLine cmdLine = CommandLine.parse(line);
		cmdLine.addArgument(f.getAbsolutePath());
		DefaultExecutor exe = new DefaultExecutor();
		exe.execute(cmdLine, rh);
		System.out.println("==end=========");
	}

	public void $7控制一個exe的開始和kill() throws Exception {
		DefaultExecuteResultHandler rh = new DefaultExecuteResultHandler();
		File f = new File(ClassLoader.getSystemResource("執行/123.txt").toURI());
		String line = "notepad.exe";
		CommandLine cmdLine = CommandLine.parse(line);
		cmdLine.addArgument(f.getAbsolutePath());
		DefaultExecutor exe = new DefaultExecutor();
		exe.setProcessDestroyer(new ProcessDestroyer() {
			@Override
			public int size() {
				return 0;
			}

			@Override
			public boolean remove(Process process) {
				System.out.println("remove" + process);
				willCloseProcess = null;
				return false;
			}

			@Override
			public boolean add(Process process) {
				System.out.println("add" + process);
				willCloseProcess = process;
				return false;
			}
		});
		exe.execute(cmdLine, rh);
		System.out.println("==exec end=========");
		System.out.println("===wait 3sec to kill exec");
		Thread.sleep(3000);
		willCloseProcess.destroy();
		Thread.sleep(3000);
		System.out.println("=kill exec end===");
	}

	public static class CollectingLogOutputStream extends LogOutputStream {
		private final List<String> lines = new LinkedList<String>();

		@Override
		protected void processLine(String line, int level) {
			lines.add(line);
		}

		public List<String> getLines() {
			return lines;
		}
	}

	public static class MyResultHandler implements ExecuteResultHandler {

		@Override
		public void onProcessComplete(int exitValue) {
			System.out.println("ok -->" + exitValue);
		}

		@Override
		public void onProcessFailed(ExecuteException e) {
			System.out.println("error");
			e.printStackTrace();
		}

	}
}
