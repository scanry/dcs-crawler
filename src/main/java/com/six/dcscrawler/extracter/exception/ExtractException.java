package com.six.dcscrawler.extracter.exception;

import com.six.dcscrawler.worker.exception.CrawlException;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年3月27日 上午10:10:30
 */
public abstract class ExtractException extends CrawlException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8842899171102267005L;

	public ExtractException(String type, String message) {
		super(type, message);
	}

	public ExtractException(String type, String message, Throwable cause) {
		super(type, message, cause);
	}

}
