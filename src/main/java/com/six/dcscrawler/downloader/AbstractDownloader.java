package com.six.dcscrawler.downloader;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.six.dcscrawler.downloader.exception.HttpStatus403DownloadException;
import com.six.dcscrawler.downloader.exception.HttpStatus502DownloadException;
import com.six.dcscrawler.downloader.exception.HttpStatus503DownloadException;
import com.six.dcscrawler.downloader.exception.HttpStatus504DownloadException;
import com.six.dcscrawler.downloader.exception.OtherDownloadException;
import com.six.dcscrawler.downloader.exception.UnknowHttpStatusDownloadException;
import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.modle.Page;

/**
 * @author sixliu E-mail:359852326@qq.com
 * @version 创建时间：2016年5月16日 下午8:17:36 类说明
 */
public abstract class AbstractDownloader implements Downloader{

	protected final static Logger log = LoggerFactory.getLogger(AbstractDownloader.class);

	private String downloaderPath;
	private long downloadTimeout;
	private HttpProxy httpProxy;
	private String useAgent;

	public AbstractDownloader(String downloaderPath, long downloadTimeout, String useAgent, HttpProxy proxy) {
		this.downloaderPath = downloaderPath;
		this.downloadTimeout = downloadTimeout;
		this.useAgent = useAgent;
		this.httpProxy = proxy;
	}

	protected String getDownloaderPath() {
		return downloaderPath;
	}

	protected long getDownloadTimeout() {
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
			throw new OtherDownloadException("page is null or page's url is blank");
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
				throw new UnknowHttpStatusDownloadException();
			}
		}
	}
	
	protected static void sleep(long time){
		if(time>0){
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {}
		}
	}
	@Override
	public void setValue(String selector, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void click(String selector) {
		throw new UnsupportedOperationException();
	}
}
