package com.six.dcscrawler.downer.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/**
 * 源数据未找到异常
 * @author weijiyong@tospur.com
 *
 */
public class RawDataNotFoundException extends DownerException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7272831990726015341L;

	public RawDataNotFoundException() {
		super(CrawlExceptionType.UNFOUND_PAGE_CACHE_EXCEPTION);
	}
	
	public RawDataNotFoundException(String message) {
		super(CrawlExceptionType.UNFOUND_PAGE_CACHE_EXCEPTION,message);
	}

	public RawDataNotFoundException(String message, Throwable cause) {
		super(CrawlExceptionType.UNFOUND_PAGE_CACHE_EXCEPTION,message, cause);
	}
}
