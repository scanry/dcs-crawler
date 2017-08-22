package com.six.dcscrawler.downer.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/**   
* @author liusong  
* @date   2017年8月21日 
* @email  359852326@qq.com 
*/
public class HttpStatus304DownloadException extends DownerException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6968368001437723592L;

	public HttpStatus304DownloadException() {
		super(CrawlExceptionType.DOWNER_304_HTTP_STATUS_EXCEPTION);
	}
	
	public HttpStatus304DownloadException(String message) {
		super(CrawlExceptionType.DOWNER_304_HTTP_STATUS_EXCEPTION,message);
	}

	public HttpStatus304DownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_304_HTTP_STATUS_EXCEPTION,message, cause);
	}

}
