package com.six.dcscrawler.worker.exception;
/**   
* @author liusong  
* @date   2017年8月21日 
* @email  359852326@qq.com 
*/
public class FoundIdentifyingCodeCrawlException extends CrawlException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5799656355771059154L;

	public FoundIdentifyingCodeCrawlException() {
		super(CrawlExceptionType.CRAWLER_FOUND_IDENTIFYING_CODE);
	}
	
	public FoundIdentifyingCodeCrawlException(String message) {
		super(CrawlExceptionType.CRAWLER_FOUND_IDENTIFYING_CODE, message);
	}

	public FoundIdentifyingCodeCrawlException(String message, Throwable cause) {
		super(CrawlExceptionType.CRAWLER_FOUND_IDENTIFYING_CODE, message, cause);
	}
}
