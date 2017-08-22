package com.six.dcscrawler.service;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
public interface CrawlService {


	/**
	 * 执行指定job
	 * 
	 * @param job
	 */
	void execute(String jobName);

	/**
	 * 暂停指定job
	 * 
	 * @param jobName
	 */
	void suspend(String jobName);

	/**
	 * 继续运行指定job
	 * 
	 * @param jobName
	 */
	void goOn(String jobName);

	/**
	 * 停止指定job
	 * 
	 * @param jobName
	 */
	void stop(String jobName);

	/**
	 * 停掉所有运行job
	 */
	void stopAll();

	/**
	 * 定时调度指定job
	 * 
	 * @param job
	 */
	void schedule(String jobName);

	/**
	 * 取消定时调度指定job
	 * 
	 * @param jobName
	 */
	void unSchedule(String jobName);

}
