package com.six.dcscrawler.downer.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/**
 *@author six    
 *@date 2016年8月26日 下午4:24:25  
*/
public class IoDownerException extends DownerException{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4216827418140704678L;

	public IoDownerException() {
		super(CrawlExceptionType.DOWNER_IO_EXCEPTION);
	}
	
	public IoDownerException(String message) {
		super(CrawlExceptionType.DOWNER_IO_EXCEPTION,message);
	}

	public IoDownerException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_IO_EXCEPTION,message, cause);
	}
}
