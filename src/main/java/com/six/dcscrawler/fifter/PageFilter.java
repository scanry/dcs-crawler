package com.six.dcscrawler.fifter;
/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
public interface PageFilter {

	boolean filter(String key);
	
	boolean addRecord(String key);
}
