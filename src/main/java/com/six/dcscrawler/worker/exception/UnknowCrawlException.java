package com.six.dcscrawler.worker.exception;
/** 
* @author  作者 
* @E-mail: 359852326@qq.com 
* @date 创建时间：2017年5月19日 下午3:38:57 
*/
public class UnknowCrawlException extends CrawlException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5426964448545692680L;

	public UnknowCrawlException(String message) {
		super(CrawlExceptionType.CRAWLER_UNKNOW_EXCEPTION, message);
	}

	public UnknowCrawlException(String message, Throwable cause) {
		super(CrawlExceptionType.CRAWLER_UNKNOW_EXCEPTION, message, cause);
	}
}
