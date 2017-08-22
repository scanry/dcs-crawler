package com.six.dcscrawler.storage;

import com.six.dcscrawler.worker.AbstractCrawlerWorker;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年3月27日 上午11:32:28
 */
public class StorageFactory {

	/**
	 * 生成一个抽取器
	 * 
	 * @param worker
	 * @param extractItems
	 * @param extracterType
	 * @return
	 */
	public static Storage newStore(AbstractCrawlerWorker worker,String storageClass) {
		Storage store = null;
		return store;
	}
}
