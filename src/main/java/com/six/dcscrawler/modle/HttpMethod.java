package com.six.dcscrawler.modle;

import java.io.Serializable;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
public enum HttpMethod implements Serializable{
	GET("get"), POST("post");

	public final String value;

	HttpMethod(String value) {
		this.value = value;
	}

	public String get() {
		return value;
	}
}
