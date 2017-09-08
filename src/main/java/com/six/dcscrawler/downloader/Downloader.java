package com.six.dcscrawler.downloader;

import org.openqa.selenium.chrome.ChromeDriver;

import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.modle.Page;

/**
 * @author liusong
 * @date 2017年8月15日
 * @email 359852326@qq.com
 */
public interface Downloader extends AutoCloseable {

	String getUseAgent();
	
	HttpProxy getProxy();
	
	ChromeDriver getChromeDriver();
	
	void setProxy(HttpProxy httpProxy);
	
	void download(Page page);
	
	void setValue(String selector, String value);

	void click(String selector);

	void clearCookie();

	void close();
}
