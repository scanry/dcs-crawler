package com.six.dcscrawler.extracter.exception;

import com.six.dcscrawler.worker.exception.CrawlExceptionType;

/** 
* @author  作者 
* @E-mail: 359852326@qq.com 
* @date 创建时间：2017年5月19日 下午3:33:44 
*/
public class ManyPrimaryExtractExceptiom extends ExtractException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5892981286428064275L;

	public ManyPrimaryExtractExceptiom(String message) {
		super(CrawlExceptionType.EXTRACT_MANY_PRIMARY_EXCEPTION,message);
	}
	
	public ManyPrimaryExtractExceptiom(String message, Throwable cause) {
		super(CrawlExceptionType.EXTRACT_MANY_PRIMARY_EXCEPTION,message, cause);
	}

}
