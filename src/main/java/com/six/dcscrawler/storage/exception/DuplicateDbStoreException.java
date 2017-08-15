package com.six.dcscrawler.storage.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年5月19日 上午11:18:32
 */
public class DuplicateDbStoreException extends StoreException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -631715205227709866L;

	public DuplicateDbStoreException(String message) {
		super(CrawlExceptionType.STORE_DB_DUPLICATE_EXCEPTION, message);
	}

	public DuplicateDbStoreException(String message, Throwable cause) {
		super(CrawlExceptionType.STORE_DB_DUPLICATE_EXCEPTION, message, cause);
	}

}
