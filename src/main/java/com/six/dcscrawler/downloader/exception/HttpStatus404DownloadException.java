package com.six.dcscrawler.downloader.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/**   
* @author liusong  
* @date   2017年8月21日 
* @email  359852326@qq.com 
*/
public class HttpStatus404DownloadException extends DownloadException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5527051481333081295L;

	public HttpStatus404DownloadException() {
		super(CrawlExceptionType.DOWNER_404_HTTP_STATUS_EXCEPTION);
	}
	
	public HttpStatus404DownloadException(String message) {
		super(CrawlExceptionType.DOWNER_404_HTTP_STATUS_EXCEPTION,message);
	}

	public HttpStatus404DownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_404_HTTP_STATUS_EXCEPTION,message, cause);
	}

}
