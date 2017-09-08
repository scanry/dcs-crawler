package com.six.dcscrawler.service;

import java.util.List;

import com.six.dcsjob.model.Job;


/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
public interface JobService {

	List<Job> getScheduledJob();
}
