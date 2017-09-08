package com.six.dcscrawler.downloader.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/**   
* @author liusong  
* @date   2017年8月21日 
* @email  359852326@qq.com 
*/
public class HttpStatus403DownloadException extends DownloadException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3408502906047604549L;

	public HttpStatus403DownloadException() {
		super(CrawlExceptionType.DOWNER_403_HTTP_STATUS_EXCEPTION);
	}
	
	public HttpStatus403DownloadException(String message) {
		super(CrawlExceptionType.DOWNER_403_HTTP_STATUS_EXCEPTION,message);
	}

	public HttpStatus403DownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_403_HTTP_STATUS_EXCEPTION,message, cause);
	}

}
