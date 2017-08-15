package com.six.dcscrawler.extracter.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;

/**
 * @author six
 * @date 2016年8月25日 上午10:14:40 解析处理程序 结果抽取异常
 */
public class UnknownExtractException extends ExtractException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3712692884588128954L;

	public UnknownExtractException(String message) {
		super(CrawlExceptionType.EXTRACT_UNKNOWN_EXCEPTION, message);
	}

	public UnknownExtractException(String message, Throwable cause) {
		super(CrawlExceptionType.EXTRACT_UNKNOWN_EXCEPTION, message, cause);
	}
}
