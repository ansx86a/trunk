package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.demo.JxBrowserDemo;
import com.teamdev.jxbrowser.chromium.events.LoadListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import hook.KeyHook建置器;
import regular.正規表示式例子;
import regular.正規表示式取值;
import ui.ieOk動作.Cv處理;
import ui.ieOk動作.Dlsite讀完命名事件;
import ui.動作.拖拉檔案處理;

public class Dlsite番號重命名UI {

	private JFrame frame;
	private JTextField textField;
	private Function<String, String> cvf;

	private 拖拉檔案處理 drop1;
	private Browser browser = JxBrowserDemo.getBrowser();
	private BrowserView view;
	private JTextField textField_1;
	private Cv處理 cv處理 = new Cv處理();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		拖拉檔案處理 drop1 = new 拖拉檔案處理();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dlsite番號重命名UI window = new Dlsite番號重命名UI();
					window.drop1 = drop1;
					window.frame.setVisible(true);
					drop1.設定啟用拖拉的容器(window.frame);
					// Function<Object,Object> f=o->{return null;};
					KeyHook建置器.加入一個hook(111, o -> {
						System.out.println(o);
						return null;
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * 用來選取字串後加CV的，目前沒用到，先在UI上填文字加CV，這一段可廢可不廢，先留下來參考就好
	 */
	public void 取得選取字串() {
		String script = "var selObj = window.getSelection();";
		script += "document.toJavaSelectValue = selObj.toString();";
		browser.executeJavaScript(script);
		JSValue document = browser.executeJavaScriptAndReturnValue("document");
		JSValue value = document.asObject().getProperty("toJavaSelectValue");
	}

	/**
	 * 才不會design卡住
	 */
	public void 手動加入brower() {
		if (view == null) {
			BrowserView view = new BrowserView(browser);
			view.setBounds(14, 113, 600, 650);
			frame.getContentPane().add(view);
		}
	}

	/**
	 * Create the application.
	 */
	public Dlsite番號重命名UI() {
		initialize();
		cvf = result -> {
			textField.setText(result);
			return result;
		};
	}

	public void 執行讀檔並轉導的動作(List<File> list) {
		if (list.size() > 0) {
			File f = list.get(0);
			list.remove(0);// 跳下一筆
			textField.setText(f.getName());
			String rj = 正規表示式取值.取得第一個值(正規表示式例子.取出番號RJ, f.getName());
			// 取不到番號就做不下去
			if (rj.length() == 0) {
				return;
			}
			String url1 = "http://www.dlsite.com/maniax/work/=/product_id/%s.html";
			System.out.println(String.format(url1, rj.toUpperCase()));
			// browser.removeLoadListener(browser.getlo);
			for (LoadListener o : browser.getLoadListeners()) {
				browser.removeLoadListener(o);
			}
			browser.addLoadListener(new Dlsite讀完命名事件(f, rj.toUpperCase(), cvf));
			browser.loadURL(String.format(url1, rj.toUpperCase()));

		} else {
			textField.setText("沒了");

		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 600, 693);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(14, 30, 540, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("讀下一筆");
		// 按鈕後的讀檔的動作
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<File> list = drop1.getList();
				執行讀檔並轉導的動作(list);
			}
		});
		btnNewButton.setBounds(14, 57, 99, 27);
		frame.getContentPane().add(btnNewButton);

		textField_1 = new JTextField();
		textField_1.setBounds(167, 58, 116, 25);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JButton btncv = new JButton("加入cv");
		btncv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cv處理.addCv(textField_1.getText());
			}
		});
		btncv.setBounds(297, 57, 99, 27);
		frame.getContentPane().add(btncv);
		// 加入brower到panel中
		手動加入brower();
	}
}
