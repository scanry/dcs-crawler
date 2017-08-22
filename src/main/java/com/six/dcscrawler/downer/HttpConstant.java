package com.six.dcscrawler.downer;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author sixliu E-mail:359852326@qq.com
 * @version 创建时间：2016年5月16日 下午8:57:30 类说明
 */
public class HttpConstant {

	public final static Map<String, String> headMap = new HashMap<String, String>();
	public final static String REFERER = "Referer";
	public final static String HOST = "Host";
	public final static String CONTENT_TYPE = "Content-Type";
	public final static String CONTENT_TYPE_FORM_VALUE = "application/x-www-form-urlencoded";
	public final static String ORIGIN = "Origin";
	public final static String COOKIE = "Cookie";// ;Content-Type
	public final static String USERAGENT = "User-Agent";// ;Origin
	public final static String COOKIE_XSRF = "_xsrf";// ;
	public final static String X_Xsrftoken = "X-Xsrftoken";
	public final static String COOKIE_SPLIT = ";";
	public final static String DEFAULT_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36";
	public final static String LOCAL_ADDRESS = "http.route.local-address";
	public final static String PROXY_CONNECTION = "Proxy-Connection";
	public final static String CONNECTION = "Connection";
	public final static String KEEP_ALIVE = "keep-alive";
	public final static long HTML_MAX_CONTENT_LENGTH = 1024 * 1000 * 1024 * 10;
	public final static Map<String, String> USER_AGENT_MAP=Maps.newHashMap();
	
	public static int REDIRECT_TIMES = 10;

	
	static {
		USER_AGENT_MAP.put("default","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		USER_AGENT_MAP.put("macoschrome","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		USER_AGENT_MAP.put("android","Dalvik/1.6.0 (Linux; U; Android 4.4.2; SM-N9006 Build/KOT49H)");
		headMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headMap.put("Accept-Language", "zh-CN,zh;q=0.8");
		headMap.put("Cache-Control", "max-age=0");
		headMap.put("Upgrade-Insecure-Requests", "1");// User-Agent:
	}

}
