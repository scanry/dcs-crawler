package com.six.dcscrawler.urlfetcher;

import java.util.List;
import java.util.Map;

import com.six.dcscrawler.modle.Page;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
public interface UrlFetcher {

	List<Page> fetch(Page page,Map<String, List<String>> extractResults);
}
