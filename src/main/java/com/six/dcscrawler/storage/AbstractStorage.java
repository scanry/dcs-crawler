package com.six.dcscrawler.storage;
/**   
* @author liusong  
* @date   2017年8月22日 
* @email  359852326@qq.com 
*/

import com.six.dcscrawler.worker.AbstractCrawlerWorker;

public abstract class AbstractStorage implements Storage{

	private AbstractCrawlerWorker worker;

	public AbstractStorage(AbstractCrawlerWorker worker) {
		this.worker = worker;
	}

	protected AbstractCrawlerWorker getWorker() {
		return worker;
	}
}
