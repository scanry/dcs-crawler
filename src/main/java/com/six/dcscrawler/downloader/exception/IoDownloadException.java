package com.six.dcscrawler.downloader.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/**
 *@author six    
 *@date 2016年8月26日 下午4:24:25  
*/
public class IoDownloadException extends DownloadException{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4216827418140704678L;

	public IoDownloadException() {
		super(CrawlExceptionType.DOWNER_IO_EXCEPTION);
	}
	
	public IoDownloadException(String message) {
		super(CrawlExceptionType.DOWNER_IO_EXCEPTION,message);
	}

	public IoDownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_IO_EXCEPTION,message, cause);
	}
}
