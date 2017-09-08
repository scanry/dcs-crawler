package com.six.dcscrawler.downloader.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;

/**
 * 
 * 502异常处理类
 * @author weijiyong@tospur.com
 *
 */
public class HttpStatus502DownloadException extends DownloadException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2606190971403749267L;

	public HttpStatus502DownloadException() {
		super(CrawlExceptionType.DOWNER_502_HTTP_STATUS_EXCEPTION);
	}
	
	public HttpStatus502DownloadException(String message) {
		super(CrawlExceptionType.DOWNER_502_HTTP_STATUS_EXCEPTION,message);
	}

	public HttpStatus502DownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_502_HTTP_STATUS_EXCEPTION,message, cause);
	}
}
