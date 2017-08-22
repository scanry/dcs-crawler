package com.six.dcscrawler.downer;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.six.dcscrawler.downer.exception.HttpStatus403DownloadException;
import com.six.dcscrawler.downer.exception.HttpStatus502DownloadException;
import com.six.dcscrawler.downer.exception.HttpStatus503DownloadException;
import com.six.dcscrawler.downer.exception.HttpStatus504DownloadException;
import com.six.dcscrawler.downer.exception.OtherDownerException;
import com.six.dcscrawler.downer.exception.UnknowHttpStatusDownerException;
import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.modle.Page;
import com.six.dcscrawler.worker.AbstractCrawlerWorker;

/**
 * @author sixliu E-mail:359852326@qq.com
 * @version 创建时间：2016年5月16日 下午8:17:36 类说明
 */
public abstract class AbstractDowner implements Downer, AutoCloseable {

	protected final static Logger log = LoggerFactory.getLogger(AbstractDowner.class);

	private AbstractCrawlerWorker worker;
	private int downloadTimeout;
	private HttpProxy httpProxy;
	private String useAgent;

	public AbstractDowner(AbstractCrawlerWorker worker, int downloadTimeout, String useAgent, HttpProxy proxy) {
		this.worker = worker;
		this.downloadTimeout = downloadTimeout;
		this.useAgent = useAgent;
		this.httpProxy = proxy;
	}

	protected AbstractCrawlerWorker getWorker() {
		return worker;
	}

	protected int getDownloadTimeout() {
		return downloadTimeout;
	}
	
	@Override
	public String getUseAgent() {
		return useAgent;
	}

	@Override
	public HttpProxy getProxy() {
		return httpProxy;
	}
	
	@Override
	public void setProxy(HttpProxy proxy) {
		 this.httpProxy=proxy;
		 doSetProxy(proxy);
	}

	protected abstract void insideDownload(Page page);
	
	protected abstract void doSetProxy(HttpProxy proxy);

	/**
	 * 记录上一次请求的url ，如果跟上一次请求url一样的话那么不下载
	 */
	public void download(Page page){
		if (null == page || StringUtils.isBlank(page.getUrl())) {
			throw new OtherDownerException("page is null or page's url is blank");
		}
		if (page.getNeedDownload() == 1) {
			insideDownload(page);
			int httpCode = page.getHttpCode();
			if (httpCode == 200) {

			} else if (httpCode == 304) {
				throw new HttpStatus502DownloadException();
			} else if (httpCode == 502) {
				throw new HttpStatus502DownloadException();
			} else if (httpCode == 503) {
				throw new HttpStatus503DownloadException();
			} else if (httpCode == 504) {
				throw new HttpStatus504DownloadException();
			} else if (httpCode == 403) {
				throw new HttpStatus403DownloadException();
			} else {
				throw new UnknowHttpStatusDownerException();
			}
		}
	}

	public WebDriver getWebDriver() {
		return null;
	}

	public String getWindowHandle() {
		return null;
	}

	public WebElement findWebElement(String xpath) {
		return null;
	}

	public List<WebElement> findWebElements(String xpath) {
		return null;
	}

	public void click(WebElement webElement, String xpath) {

	}
}
