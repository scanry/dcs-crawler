package com.six.dcscrawler.storage.exception;

import com.six.dcscrawler.worker.exception.CrawlException;

/** 
* @author  作者 
* @E-mail: 359852326@qq.com 
* @date 创建时间：2017年2月23日 下午3:13:08 
*/
public abstract class StoreException extends CrawlException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4971967663948212856L;


	public StoreException(String type, String message) {
		super(type, message);
	}

	public StoreException(String type, String message, Throwable cause) {
		super(type, message, cause);
	}
}
