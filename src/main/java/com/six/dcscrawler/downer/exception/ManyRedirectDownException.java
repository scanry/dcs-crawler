package com.six.dcscrawler.downer.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/**
 *@author six    
 *@date 2016年8月30日 下午2:42:32  
*/
public class ManyRedirectDownException extends DownerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3836363279563745918L;

	public ManyRedirectDownException() {
		super(CrawlExceptionType.DOWNER_MANY_REDIRECT_EXCEPTION);
	}
	
	public ManyRedirectDownException(String message) {
		super(CrawlExceptionType.DOWNER_MANY_REDIRECT_EXCEPTION,message);
	}

	public ManyRedirectDownException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_MANY_REDIRECT_EXCEPTION,message, cause);
	}
}
