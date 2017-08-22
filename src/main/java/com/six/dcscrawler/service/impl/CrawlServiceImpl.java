package com.six.dcscrawler.service.impl;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.six.dcscrawler.service.CrawlService;
import com.six.dcscrawler.service.JobService;
import com.six.dcsjob.Job;
import com.six.dcsjob.executor.Executor;
import com.six.dcsjob.executor.ExecutorImpl;
import com.six.dcsjob.scheduler.Scheduler;
import com.six.dcsjob.scheduler.SchedulerImpl;
import com.six.dcsnodeManager.Node;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
@Service
public class CrawlServiceImpl implements CrawlService,InitializingBean,DisposableBean{

	private Scheduler scheduler ;
	private Executor  executor;
	private JobService jobService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Node currentNode=new Node();
		int trheads=0;
		scheduler=new SchedulerImpl(currentNode);
		executor=new ExecutorImpl(currentNode,trheads);
		loadScheduledJobs();
	}
	

	private void loadScheduledJobs(){
		List<Job> scheduledJobs=jobService.getScheduledJob();
		for(Job job:scheduledJobs){
			scheduler.schedule(job);
		}
	}
	
	@Override
	public void execute(String jobName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void suspend(String jobName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void goOn(String jobName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void stop(String jobName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void stopAll() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void schedule(String jobName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void unSchedule(String jobName) {
		// TODO Auto-generated method stub
		
	}
	


	@Override
	public void destroy() throws Exception {
		if(null!=executor){
			executor.shutdown();
		}
		if(null!=scheduler){
			scheduler.shutdown();
		}
	}

}
