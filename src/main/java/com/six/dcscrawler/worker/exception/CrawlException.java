package com.six.dcscrawler.worker.exception;

import com.six.dcsjob.work.exception.WorkerException;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年5月19日 上午11:46:24
 */
public abstract class CrawlException extends WorkerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6837835105330279257L;

	public CrawlException(String type) {
		super(type);
	}
	public CrawlException(String type, String message) {
		super(type, message);
	}

	public CrawlException(String type, String message, Throwable cause) {
		super(type, message, cause);
	}

}
