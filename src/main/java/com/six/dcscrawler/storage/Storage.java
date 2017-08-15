package com.six.dcscrawler.storage;

import java.util.List;
import java.util.Map;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
* 
* 爬虫采集数据存储接口
*/
public interface Storage {

	/**
	 * 爬虫采集数据存储接口
	 * @param list
	 * @return 返回存储的结果数
	 */
	int store(List<Map<String,String>> list);
}
