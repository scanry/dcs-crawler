package com.six.dcscrawler.modle;


/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
public enum PostType {

	JSON("application/json"), FORM("application/form"),PAY_LOAD("text/html");

	private final String value;

	PostType(String value) {
		this.value = value;
	}

	public String get() {
		return value;
	}

	public static PostType paser(String type) {
		if ("json".equals(type)) {
			return JSON;
		} else {
			return FORM;
		}
	}

}
