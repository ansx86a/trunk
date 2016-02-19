package com.teamdev.jxbrowser.chromium.demo;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class JxBrowserDemo {
	public static void main(String[] args) {
		Browser browser = new Browser();
		BrowserView view = new BrowserView(browser);

		JFrame frame = new JFrame();
		frame.add(view, BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setVisible(true);

		browser.loadURL("http://www.google.com");

		// new com.teamdev.jxbrowser.chromium.demo.JxBrowserDemo();
	}

	public static Browser getBrowser() {
		return new Browser();
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

		browser.loadURL("http://www.google.com");
	}
}
