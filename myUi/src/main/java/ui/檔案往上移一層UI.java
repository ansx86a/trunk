package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import filesService.檔案目錄上下層處理;
import ui.動作.拖拉檔案處理;

public class 檔案往上移一層UI {
	private JFrame frame;
	private JTextField textField;

	private 拖拉檔案處理 drop1;
	private JTextField textField_1;

	public 檔案往上移一層UI() {
		initialize();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		拖拉檔案處理 drop1 = new 拖拉檔案處理();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					檔案往上移一層UI window = new 檔案往上移一層UI();
					window.drop1 = drop1;
					window.frame.setVisible(true);
					drop1.設定啟用拖拉的容器(window.frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 438, 195);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(65, 69, 320, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("執行");
		// 按鈕後的讀檔的動作
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<File> list = drop1.getList();
				if (list.size() > 0) {
					檔案目錄上下層處理 fs = new 檔案目錄上下層處理();
					String fn = list.get(0).getAbsolutePath();
					fs.往上移一層目錄controller(fn);
				}
				list.clear();
			}
		});
		btnNewButton.setBounds(14, 107, 99, 27);
		frame.getContentPane().add(btnNewButton);

	}
}
