package com.six.dcscrawler.service;

import com.six.dcsnodeManager.Node;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
public interface ClusterManagerService {

	boolean isSingle();
	
	boolean isMaster();
	
	Node getMaster();
}
