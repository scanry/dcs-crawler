package com.six.dcscrawler.downloader.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;


/** 
* @author  作者 
* @E-mail: 359852326@qq.com 
* @date 创建时间：2017年5月19日 上午11:38:32 
*/
public class OtherDownloadException extends DownloadException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6422920541089653430L;

	public OtherDownloadException() {
		super(CrawlExceptionType.DOWNER_OTHER_EXCEPTION);
	}
	
	public OtherDownloadException(String message) {
		super(CrawlExceptionType.DOWNER_OTHER_EXCEPTION,message);
	}

	public OtherDownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_OTHER_EXCEPTION,message, cause);
	}
}