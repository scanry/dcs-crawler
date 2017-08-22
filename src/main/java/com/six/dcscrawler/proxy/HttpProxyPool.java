package com.six.dcscrawler.proxy;

import com.six.dcscrawler.modle.HttpProxy;

/**
 * ip代理池
 * 
 * @author liusong
 *
 */
public interface HttpProxyPool extends AutoCloseable{

	/**
	 * 获取一个可用的代理
	 * 
	 * @param host
	 * @return
	 */
	public default HttpProxy getHttpProxy(String host,long restTime){
		return null;
	}
	
	public default void destroy(){}
}
