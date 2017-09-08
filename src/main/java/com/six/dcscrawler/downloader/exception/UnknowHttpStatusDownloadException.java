package com.six.dcscrawler.downloader.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;

/**
 * @author six
 * @date 2016年8月18日 上午11:03:42 未知的 http 响应 状态异常
 */
public class UnknowHttpStatusDownloadException extends DownloadException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3389980017744878875L;

	public UnknowHttpStatusDownloadException() {
		super(CrawlExceptionType.DOWNER_UNKNOW_HTTP_STATUS_EXCEPTION);
	}
	
	public UnknowHttpStatusDownloadException(String message) {
		super(CrawlExceptionType.DOWNER_UNKNOW_HTTP_STATUS_EXCEPTION, message, null);
	}

	public UnknowHttpStatusDownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_UNKNOW_HTTP_STATUS_EXCEPTION, message, cause);
	}

}
