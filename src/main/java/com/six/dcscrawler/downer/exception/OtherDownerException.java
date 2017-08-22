package com.six.dcscrawler.downer.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/** 
* @author  作者 
* @E-mail: 359852326@qq.com 
* @date 创建时间：2017年5月19日 上午11:38:32 
*/
public class OtherDownerException extends DownerException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6422920541089653430L;

	public OtherDownerException() {
		super(CrawlExceptionType.DOWNER_OTHER_EXCEPTION);
	}
	
	public OtherDownerException(String message) {
		super(CrawlExceptionType.DOWNER_OTHER_EXCEPTION,message);
	}

	public OtherDownerException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_OTHER_EXCEPTION,message, cause);
	}
}