package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.demo.JxBrowserDemo;
import com.teamdev.jxbrowser.chromium.events.LoadListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import ui.ieOk動作.愛看漫讀完漫畫頁事件;

public class 捉漫畫用 {

	public static void main(String[] args) throws InterruptedException {
		Browser browser = JxBrowserDemo.getBrowser();
		BrowserView view = new BrowserView(browser);
		JFrame frame = new JFrame();
		frame.add(view, BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setVisible(true);
		愛看漫讀完漫畫頁事件 event = new 愛看漫讀完漫畫頁事件();
		browser.addLoadListener(event);
		browser.loadURL("http://www.ikanman.com/comic/4773/43867.html#p=1");
//		while (true) {
//			if (event.getIsnext()) {
//				System.out.println("===========next");
//				for (LoadListener o : browser.getLoadListeners()) {
//					browser.removeLoadListener(o);
//				}
//				event = new 愛看漫讀完漫畫頁事件();
//				browser.addLoadListener(event);
//				browser.loadURL("http://www.ikanman.com/comic/4773/43867.html#p=9");
//				break;
//			}
//		}
	}

}
