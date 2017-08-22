package com.six.dcscrawler.storage;

import java.util.List;
import java.util.Map;

import com.six.dcscrawler.worker.AbstractCrawlerWorker;

/**   
* @author liusong  
* @date   2017年8月22日 
* @email  359852326@qq.com 
*/
public class ConsoleStorage extends AbstractStorage{

	public ConsoleStorage(AbstractCrawlerWorker worker) {
		super(worker);
	}

	@Override
	public int store(List<Map<String, String>> list) {
		return 0;
	}

	@Override
	public void close() throws Exception {
		
	}

}
