package com.teamdev.jxbrowser.chromium.demo;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class JxBrowserDemo {
	public static void main(String[] args) {
		Browser browser = new Browser(BrowserContext.defaultContext());
		BrowserView view = new BrowserView(browser);

		JFrame frame = new JFrame();
		frame.add(view, BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setVisible(true);

		browser.loadURL("http://www.kimo.com.tw");

		// new com.teamdev.jxbrowser.chromium.demo.JxBrowserDemo();
	}

	public static Browser getBrowser() {
		return new Browser(BrowserContext.defaultContext());
	}

	public BrowserView getBrowserView(Browser browser) {
		return new BrowserView(browser);
	}

	public void test() {
		Browser browser = new Browser();
		BrowserView view = new BrowserView(browser);

		JFrame frame = new JFrame();
		frame.add(view, BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setVisible(true);

		browser.loadURL("http://www.kimo.com");
	}
}
