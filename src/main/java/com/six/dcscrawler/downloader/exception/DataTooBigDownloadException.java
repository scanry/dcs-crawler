package com.six.dcscrawler.downloader.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;

/**
 * @author six
 * @date 2016年8月18日 下午3:13:29
 *
 *       http读取数据 超过最大 Max 异常
 */
public class DataTooBigDownloadException extends DownloadException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3062149725002886914L;

	public DataTooBigDownloadException(String message) {
		super(CrawlExceptionType.DOWNER_DATA_TOO_BIG_EXCEPTION,message, null);
	}

	public DataTooBigDownloadException(String message, Throwable cause) {
		super(CrawlExceptionType.DOWNER_DATA_TOO_BIG_EXCEPTION,message, cause);
	}
}
