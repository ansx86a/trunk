import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.demo.JxBrowserDemo;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class JavaSwingSample {
	public static void main(String[] args) {
		JxBrowserDemo d = new JxBrowserDemo();
		Browser browser = d.getBrowser();
		BrowserView view = new BrowserView(browser);

		JFrame frame = new JFrame();
		frame.add(view, BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setVisible(true);

		browser.loadURL("http://www.google.com");
	}
}