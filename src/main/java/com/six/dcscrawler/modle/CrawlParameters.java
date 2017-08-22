package com.six.dcscrawler.modle;

/**
 * @author liusong
 * @date 2017年8月15日
 * @email 359852326@qq.com
 */
public interface CrawlParameters {

	/**page cache impl**/
	String PAGE_CACHE_IMPL = "pageCacheImpl";
	/**抽取项**/
	String EXTRACT_ITEMS="extractItems";
	/**网页站点**/
	String SITE = "site";
	/**是否开启robots协议**/
	String ROBOTS_ENABLE = "robots";
	/**allowForbidden**/
	String ALLOW_FORBIDDEN = "allowForbidden";
	/**访问间隔**/
	String VISIT_INTERVAL = "visitInterval";
	/**是否去重**/
	String IS_DO_DUPLICATION = "isDoDuplication";
	/**userAgent**/
	String USER_AGENT = "userAgent";
	/**页面最大重试次数**/
	String PAGE_RETRY_MAX_COUNT = "page_retry_max_count";
	// 验证码
	String IDENTIFYING_CODE_CSS_ARRAY = "identifyingCodeCssArray";
	/** 下载器参数 **/
	// 下载器类型
	String DOWNER_TYPE = "downerType";
	// 代理类型
	String HTTP_PROXY_TYPE = "httpProxyType";
	String DOWNLOAD_TIMEOUT = "downloadTimeout";
	// 代理休息时间
	String HTTP_PROXY_REST_TIME = "httpProxyRestTime";

	String OPEN_DOWN_CACHE = "openDownCache";

	String USE_DOWN_CACHE = "useDownCache";

	// 抽取类型
	String EXTRACTER_TYPE = "extracterType";

	// 结果保存class
	String RESULT_STORAGE_CLASS= "storageClass";

	String IS_SNAPSHOT_TABLE = "isSnapshotTable";

	String FIXED_TABLE_NAME = "fixedTableName";

	// 结果存储sql
	String DB_URL = "dbUrl";
	String DB_DRIVER_CLASS_NAME = "dbDriverClassName";
	// 结果存储sql
	String DB_USER = "dbUser";
	// 结果存储sql
	String DB_PASSWD = "dbPasswd";

	// 结果发送http url
	String SEND_HTTP_URL = "sendHttpUrl";

	String SEND_HTTP_METHOD = "sendHttpMethod";

	String FIND_ELEMENT_TIME_OUT = "findElementTimeout";

	String CREATE_TABLE_SQL_TEMPLATE = "createTableSqlTemplate";

	String SELECT_SQL_TEMPLATE = "selectSqlTemplate";

	String INSERT_SQL_TEMPLATE = "insertSqlTemplate";

	String UPDATE_SQL_TEMPLATE = "updateSqlTemplate";

	String DEL_SQL_TEMPLATE = "delSqlTemplate";
}
