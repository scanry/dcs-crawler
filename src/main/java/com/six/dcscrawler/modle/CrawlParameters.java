package com.six.dcscrawler.modle;

/**
 * @author liusong
 * @date 2017年8月15日
 * @email 359852326@qq.com
 */
public interface CrawlParameters {

	String SITE = "site";
	String ROBOTS_ENABLE = "robots";
	String ALLOW_FORBIDDEN = "allowForbidden";
	String VISIT_INTERVAL = "visitInterval";
	String IS_DE_DUPLICATION="isDeDuplication";
	String USER_AGENT="userAgent";
	/** 下载器参数 **/
	// 下载器类型
	String DOWNER_TYPE = "downerType";
	// 代理类型
	String HTTP_PROXY_TYPE = "httpProxyType";
	// 代理休息时间
	String HTTP_PROXY_REST_TIME = "httpProxyRestTime";

	String OPEN_DOWN_CACHE = "openDownCache";

	String USE_DOWN_CACHE = "useDownCache";

	String HTTP_CONNECT_TIMEOUT = "httpConnectTimeOut";

	String HTTP_WRITE_TIMEOUT = "httpWriteTimeOut";

	String HTTP_READ_TIMEOUT = "httpReadTimeOut";

	int DEFAULT_HTTP_CONNECT_TIMEOUT = 60;

	int DEFAULT_HTTP_WRITE_TIMEOUT = 60;

	int DEFAULT_HTTP_READ_TIMEOUT = 60;

	// 是否保留源数据
	String IS_SAVE_RAW_DATA = "isSaveRawData";

	// 是否使用源数据
	String IS_USE_RAW_DATA = "isUseRawdata";

	/** 下载器参数 **/

	int DEFAULT_IS_SAVE_RAW_DATA = 0;

	int DEFAULT_IS_USE_RAW_DATA = 0;

	// 抽取类型
	String EXTRACTER_TYPE = "extracterType";
	// 结果存储key
	String RESULT_STORE_CLASS = "resultStoreClass";

	// 结果store 类型
	String RESULT_STORE_TYPE = "storeType";

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
