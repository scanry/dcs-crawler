package com.six.dcscrawler.downer.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/** 
* @author  作者 
* @E-mail: 359852326@qq.com 
* @date 创建时间：2017年3月27日 上午9:44:10 
* 下载超时异常
*/
public class TimeoutDownerException extends DownerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7313906660397137435L;


	public TimeoutDownerException() {
		super(CrawlExceptionType.DOWNER_TIMEOUT_EXCEPTION);
	}
	
	public TimeoutDownerException(String message) {
		super(CrawlExceptionType.DOWNER_TIMEOUT_EXCEPTION,message);
	}

	public TimeoutDownerException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_TIMEOUT_EXCEPTION,message, cause);
	}
}
