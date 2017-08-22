package com.six.dcscrawler.proxy;


import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.utils.UrlUtils;

public class DynamicHttpProxyPool implements HttpProxyPool{

	protected final static Logger log = LoggerFactory.getLogger(DynamicHttpProxyPool.class);
	
	private String getProxyUrlPre="http://172.18.180.225:9300/proxy/";
	
	@Override
	public HttpProxy getHttpProxy(String host, long restTime) {
		HttpProxy httpProxy=null;
		host=UrlUtils.getHost(host);
		String getProxyUrl=getProxyUrlPre+host;
		while(true){
			try {
				String proxy=Jsoup.connect(getProxyUrl).get().text();
				httpProxy=HttpProxy.build(proxy,"");
				break;
			} catch (Exception e) {
				log.error("get dynamic http proxy err", e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {}
			}
		}
		return httpProxy;
	}
	public void close() {}
}
