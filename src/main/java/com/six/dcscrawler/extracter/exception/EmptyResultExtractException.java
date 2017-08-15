package com.six.dcscrawler.extracter.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;

/**
 * @author six
 * @date 2016年8月25日 下午2:16:54
 */
public class EmptyResultExtractException extends ExtractException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7641480285086513770L;

	public EmptyResultExtractException(String message) {
		super(CrawlExceptionType.EXTRACT_EMPTY_EXCEPTION, message);
	}

	public EmptyResultExtractException(String message, Throwable cause) {
		super(CrawlExceptionType.EXTRACT_EMPTY_EXCEPTION, message, cause);
	}

}
