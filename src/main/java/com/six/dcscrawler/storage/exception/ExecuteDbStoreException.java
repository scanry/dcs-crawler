package com.six.dcscrawler.storage.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年5月19日 上午11:15:03
 */
public class ExecuteDbStoreException extends StoreException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 917152488589532795L;

	public ExecuteDbStoreException(String message) {
		super(CrawlExceptionType.STORE_DB_EXECUTE_EXCEPTION, message);
	}

	public ExecuteDbStoreException(String message, Throwable cause) {
		super(CrawlExceptionType.STORE_DB_EXECUTE_EXCEPTION, message, cause);
	}
}
