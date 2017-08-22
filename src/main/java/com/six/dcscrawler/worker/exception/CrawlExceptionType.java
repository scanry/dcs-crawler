package com.six.dcscrawler.worker.exception;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年5月19日 上午11:11:08
 * 
 *       worker异常类型 定义常数
 * 
 */
public interface CrawlExceptionType {

	// worker 处理异常
	
	/** worker业务 未知处理异常 **/
	String CRAWLER_UNKNOW_EXCEPTION = "crawler_unknow";

	/**页面发现验证码**/
	String CRAWLER_FOUND_IDENTIFYING_CODE="found_identifying_code";
	// 页面下载器异常定义

	/** 页面下载器超时异常 **/
	String DOWNER_TIMEOUT_EXCEPTION = "downer_timeout";
	/** 页面下载器io异常 **/
	String DOWNER_IO_EXCEPTION = "downer_io";
	/** 页面下载器数据太大异常 **/
	String DOWNER_DATA_TOO_BIG_EXCEPTION = "downer_data_too_big";
	/** 页面下载器太多重定向异常 **/
	String DOWNER_MANY_REDIRECT_EXCEPTION = "downer_many_redirect";
	/** 页面下载器未知http状态码异常 **/
	String DOWNER_UNKNOW_HTTP_STATUS_EXCEPTION = "downer_unknow_http_status";
	/** 页面下载器202http状态吗异常**/
	String DOWNER_202_HTTP_STATUS_EXCEPTION = "downer_202_http_status";
	/** 页面下载器304http状态吗异常**/
	String DOWNER_304_HTTP_STATUS_EXCEPTION = "downer_304_http_status";
	/** 页面下载器403http状态吗异常**/
	String DOWNER_403_HTTP_STATUS_EXCEPTION = "downer_403_http_status";
	/** 页面下载器404http状态吗异常**/
	String DOWNER_404_HTTP_STATUS_EXCEPTION = "downer_404_http_status";
	/** 页面下载器502http状态吗异常 **/
	String DOWNER_502_HTTP_STATUS_EXCEPTION = "downer_502_http_status";

	/** 页面下载器503http状态吗异常 **/
	String DOWNER_503_HTTP_STATUS_EXCEPTION = "downer_503_http_status";
	/** 页面下载器504http状态吗异常 **/
	String DOWNER_504_HTTP_STATUS_EXCEPTION = "downer_504_http_status";
	/** 页面下载器其他异常 **/
	String DOWNER_OTHER_EXCEPTION = "downer_other";

	/** 没有找到页面缓存异常 **/
	String UNFOUND_PAGE_CACHE_EXCEPTION = "unfound_page_cache";

	// 页面抽取异常定义
	/** 太多主抽取结果异常 **/
	String EXTRACT_MANY_PRIMARY_EXCEPTION = "extract_many_primary";
	/** 抽取未知异常 **/
	String EXTRACT_UNKNOWN_EXCEPTION = "extract_unknow";
	/** 抽取结果为空异常 **/
	String EXTRACT_EMPTY_EXCEPTION = "extract_empty_result";
	/** 没有找到抽取元素path异常 **/
	String EXTRACT_UNFOUND_PATH_EXCEPTION = "extract_unfound_path";
	/** 无效的抽取元素path异常 **/
	String EXTRACT_INVALID_PATH_EXCEPTION = "extract_invalid_path";
	/** store数据存储异常定义 **/
	String STORE_DB_EXECUTE_EXCEPTION = "store_db_execute";
	String STORE_DB_DUPLICATE_EXCEPTION = "store_db_duplicate";
	
}
