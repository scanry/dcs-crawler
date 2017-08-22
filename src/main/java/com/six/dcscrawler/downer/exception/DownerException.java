package com.six.dcscrawler.downer.exception;

import com.six.dcscrawler.worker.exception.CrawlException;

/**
 * @author six
 * @date 2016年8月26日 下午2:14:46 下载器异常：
 *       <p>
 *       1.下载超时异常
 *       </p>
 *       <p>
 *       2.重定向太多异常
 *       </p>
 *       <p>
 *       3.未知状态码异常
 *       </p>
 * 
 * 
 */
public abstract class DownerException extends CrawlException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7822200945702547159L;

	public DownerException(String type) {
		super(type);
	}

	public DownerException(String type, String message) {
		super(type, message);
	}

	public DownerException(String type, String message, Throwable cause) {
		super(type, message, cause);
	}
}
