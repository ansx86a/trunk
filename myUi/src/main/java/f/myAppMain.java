package f;

import java.awt.EventQueue;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.demo.JxBrowserDemo;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.JPanel;

public class myAppMain {

	private JFrame frame;
	private JTextField textField;

	private 拖拉檔案處理 drop1;
	private Browser browser = JxBrowserDemo.getBrowser();
	private BrowserView view;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		拖拉檔案處理 drop1 = new 拖拉檔案處理();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myAppMain window = new myAppMain();
					window.drop1 = drop1;
					window.frame.setVisible(true);
					window.frame
							.setDropTarget(new DropTarget(window.frame, DnDConstants.ACTION_REFERENCE, drop1, true));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
	public myAppMain() {
		initialize();
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
		textField.setBounds(14, 30, 266, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("讀下一筆");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<File> list = drop1.getList();
				if (list.size() > 0) {
					textField.setText(list.get(0).getAbsolutePath());
					list.remove(list.get(0));
				} else {
					textField.setText("沒了");
					browser.loadURL("http://www.google.com");
				}
			}
		});
		btnNewButton.setBounds(294, 29, 99, 27);
		frame.getContentPane().add(btnNewButton);

		手動加入brower(); 
	}
}
