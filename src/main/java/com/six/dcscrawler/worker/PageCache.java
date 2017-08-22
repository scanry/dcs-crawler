package com.six.dcscrawler.worker;

import com.six.dcscrawler.modle.Page;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年5月15日 下午5:57:01
 */
public interface PageCache extends AutoCloseable{

	static PageCache DEFAULT_INSTANCE=new PageCache(){

		@Override
		public void close() throws Exception {	
		}

		@Override
		public void write(Page page) {}

		@Override
		public Page read(Page page) {
			return null;
		}
		
	};
	
	void write(Page page);

	Page read(Page page);
	
}
