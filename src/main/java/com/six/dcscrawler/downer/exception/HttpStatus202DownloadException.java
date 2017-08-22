package com.six.dcscrawler.downer.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/**   
* @author liusong  
* @date   2017年8月21日 
* @email  359852326@qq.com 
*/
public class HttpStatus202DownloadException extends DownerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6532056902555758615L;

	public HttpStatus202DownloadException() {
		super(CrawlExceptionType.DOWNER_202_HTTP_STATUS_EXCEPTION);
	}
	
	public HttpStatus202DownloadException(String message) {
		super(CrawlExceptionType.DOWNER_202_HTTP_STATUS_EXCEPTION,message);
	}

	public HttpStatus202DownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_202_HTTP_STATUS_EXCEPTION,message, cause);
	}


}
