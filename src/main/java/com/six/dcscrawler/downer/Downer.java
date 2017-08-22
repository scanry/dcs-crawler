package com.six.dcscrawler.downer;

import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.modle.Page;

/**
 * @author liusong
 * @date 2017年8月15日
 * @email 359852326@qq.com
 */
public interface Downer extends AutoCloseable {

	String getUseAgent();
	
	HttpProxy getProxy();
	
	void setProxy(HttpProxy httpProxy);
	
	
	void clearCookie();

	void download(Page page);
}
