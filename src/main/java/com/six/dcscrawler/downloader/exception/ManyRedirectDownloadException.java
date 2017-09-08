package com.six.dcscrawler.downloader.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/**
 *@author six    
 *@date 2016年8月30日 下午2:42:32  
*/
public class ManyRedirectDownloadException extends DownloadException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3836363279563745918L;

	public ManyRedirectDownloadException() {
		super(CrawlExceptionType.DOWNER_MANY_REDIRECT_EXCEPTION);
	}
	
	public ManyRedirectDownloadException(String message) {
		super(CrawlExceptionType.DOWNER_MANY_REDIRECT_EXCEPTION,message);
	}

	public ManyRedirectDownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_MANY_REDIRECT_EXCEPTION,message, cause);
	}
}
