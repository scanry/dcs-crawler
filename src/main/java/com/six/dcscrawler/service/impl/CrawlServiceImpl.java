package com.six.dcscrawler.service.impl;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.six.dcscrawler.service.CrawlService;
import com.six.dcscrawler.service.JobService;
import com.six.dcsjob.model.Job;
import com.six.dcsjob.DcsJobManager;
import com.six.dcsjob.DcsJobServiceConfig;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
@Service
public class CrawlServiceImpl implements CrawlService,InitializingBean,DisposableBean{

	private DcsJobManager dcsJobManager;
	private JobService jobService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		DcsJobServiceConfig dcsJobCf=new DcsJobServiceConfig();
		dcsJobManager=new DcsJobManager(dcsJobCf);
		loadScheduledJobs();
	}
	

	private void loadScheduledJobs(){
		List<Job> scheduledJobs=jobService.getScheduledJob();
		for(Job job:scheduledJobs){
			dcsJobManager.schedule(job);
		}
	}
	
	@Override
	public void execute(String jobName) {
		dcsJobManager.execute("", jobName);
	}


	@Override
	public void suspend(String jobName) {
		dcsJobManager.suspend(jobName);
	}


	@Override
	public void goOn(String jobName) {
		dcsJobManager.goOn(jobName);
	}


	@Override
	public void stop(String jobName) {
		dcsJobManager.stop(jobName);
	}


	@Override
	public void stopAll() {
		dcsJobManager.stopAll();
	}


	@Override
	public void schedule(String jobName) {
		Job job=null;
		dcsJobManager.schedule(job);
	}


	@Override
	public void unSchedule(String jobName) {
		dcsJobManager.unschedule(jobName);
	}
	


	@Override
	public void destroy() throws Exception {
		if(null!=dcsJobManager){
			dcsJobManager.shutdown();
		}
	}

}
