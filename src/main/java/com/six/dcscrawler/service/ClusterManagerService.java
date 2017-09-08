package com.six.dcscrawler.service;

import org.apache.ignite.cluster.ClusterNode;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
public interface ClusterManagerService {

	boolean isSingle();
	
	boolean isMaster();
	
	ClusterNode getMaster();
}
