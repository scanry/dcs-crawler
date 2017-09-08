package com.six.dcscrawler.downloader.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;

/**   
* @author liusong  
* @date   2017年8月21日 
* @email  359852326@qq.com 
*/
public class HttpStatus503DownloadException extends DownloadException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2072838619756214605L;

	public HttpStatus503DownloadException() {
		super(CrawlExceptionType.DOWNER_503_HTTP_STATUS_EXCEPTION);
	}
	
	public HttpStatus503DownloadException(String message) {
		super(CrawlExceptionType.DOWNER_503_HTTP_STATUS_EXCEPTION,message);
	}

	public HttpStatus503DownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_503_HTTP_STATUS_EXCEPTION,message, cause);
	}
}
