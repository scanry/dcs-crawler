package com.six.dcscrawler.downer.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;

/**
 * @author liusong
 * @date 2017年8月21日
 * @email 359852326@qq.com
 */
public class HttpStatus504DownloadException extends DownerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2350506214983936207L;

	public HttpStatus504DownloadException() {
		super(CrawlExceptionType.DOWNER_504_HTTP_STATUS_EXCEPTION);
	}

	public HttpStatus504DownloadException(String message) {
		super(CrawlExceptionType.DOWNER_504_HTTP_STATUS_EXCEPTION, message);
	}

	public HttpStatus504DownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_504_HTTP_STATUS_EXCEPTION, message, cause);
	}

}
