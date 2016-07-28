package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import filesService.字幕;
import ui.動作.拖拉檔案處理;

public class 字幕重新命名UI {
	private JFrame frame;
	private JTextField textField;

	private 拖拉檔案處理 drop1;
	private JTextField textField_1;

	/**
	 * Create the application.
	 */
	public 字幕重新命名UI() {
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
					字幕重新命名UI window = new 字幕重新命名UI();
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
					File f = list.get(0);
					if (f.getName().length() == 0) {
						return;
					}
					字幕 sub = new 字幕();
					if (textField_1.getText().split(",").length > 1) {
						ArrayList list2 =new ArrayList(Arrays.asList(textField_1.getText().split(",")));
						System.out.println(list2);
						sub.set拆分字(list2);
					}
					try {
						sub.重新命名字幕檔(f);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						textField.setText("執行失敗");
						return;
					}
					textField.setText("執行完成ok");
				} else {
					textField.setText("執行失敗");
				}
				list.clear();
			}
		});
		btnNewButton.setBounds(14, 107, 99, 27);
		frame.getContentPane().add(btnNewButton);

		JLabel label = new JLabel("結果");
		label.setBounds(14, 72, 57, 19);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("拆分字逗號分開");
		label_1.setBounds(14, 29, 111, 19);
		frame.getContentPane().add(label_1);

		textField_1 = new JTextField();
		textField_1.setText("[,], ,.,第,話");
		textField_1.setBounds(139, 30, 246, 25);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
	}
}
