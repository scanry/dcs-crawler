package com.six.dcscrawler.downer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.worker.AbstractCrawlerWorker;


/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2016年10月14日 上午10:53:52
 */
public class DownerManager {

	final static Logger log = LoggerFactory.getLogger(DownerManager.class);

	//private Map<HttpProxy, AbstractDowner> jsDownloaders = new LinkedHashMap<>();
	//private Map<HttpProxy, AbstractDowner> noJsdownloaders = new LinkedHashMap<>();
	//private Thread checkLastActivityTimeThread;
	//private Object checkLastActivityTimeThreadObject = new Object();
	//private long maxActivityInterval = 1000 * 60 * 5;

	private DownerManager() {
//		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//			for(AbstractDowner downloader:downloaders){
//				try {
//					downloader.close();
//				} catch (Exception e) {
//					log.error("", e);
//				}
//			}
//			downloaders.clear();
//		}, "close-all-downloader-thread"));
//		checkLastActivityTimeThread = new Thread(() -> {
//			while (true) {
//				try {
//					checkLastActivityTime(jsDownloaders);
//					checkLastActivityTime(noJsdownloaders);
//				} catch (Exception e) {
//					log.error("", e);
//				}
//				synchronized (checkLastActivityTimeThreadObject) {
//					try {
//						checkLastActivityTimeThreadObject.wait(maxActivityInterval);
//					} catch (InterruptedException e) {
//					}
//				}
//			}
//		}, "check-downloader-lastActivityTime-thread");
//		checkLastActivityTimeThread.setDaemon(true);
		//checkLastActivityTimeThread.start();
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static DownerManager getInstance() {
		return DownerManagerUtils.DownerManager;
	}

	/**
	 * 单例模式 实现赖加载
	 * 
	 * @author six
	 * @email 359852326@qq.com
	 */
	private static class DownerManagerUtils {
		static DownerManager DownerManager = new DownerManager();
	}

//	private void checkLastActivityTime(Map<HttpProxy, AbstractDowner> downloaders) {
//		Iterator<AbstractDowner> jsIterator = downloaders.values().iterator();
//		AbstractDowner downloader = null;
//		while (jsIterator.hasNext()) {
//			downloader = jsIterator.next();
//			if (!downloader.isHold() && downloader.lastActivityTime() >= maxActivityInterval) {
//				downloader.close();
//				jsIterator.remove();
//			}
//		}
//	}

//	private void notifyCheckThread() {
//		synchronized (checkLastActivityTimeThreadObject) {
//			checkLastActivityTimeThreadObject.notifyAll();
//		}
//	}

//	public AbstractDowner returnAndGetDowner(AbstractCrawlWorker worker, int downloadTimeout, DownerType downerType,
//			String useAgent, AbstractDowner returnDownloader, HttpProxy proxy) {
//		if (DownerType.CHROME == downerType) {
//			return returnAndGetDowner(returnDownloader, useAgent, jsDownloaders, proxy, (useAgentP, proxyP) -> {
//				AbstractDowner downer = new ChromeDowner(worker, downloadTimeout, useAgentP, proxyP);
//				return downer;
//			});
//		} else {
//			return returnAndGetDowner(returnDownloader, useAgent, noJsdownloaders, proxy, (useAgentP, proxyP) -> {
//				AbstractDowner downer = new OkHttpDowner(worker, downloadTimeout, useAgentP, proxyP);
//				return downer;
//			});
//		}
//	}

	public AbstractDowner newDowner(AbstractCrawlerWorker worker, int downloadTimeout, DownerType downerType,
			String useAgent, HttpProxy proxy) {
		AbstractDowner downer = null;
		if (DownerType.CHROME == downerType) {
			downer = new ChromeDowner(worker, downloadTimeout, useAgent, proxy);
		}else if (DownerType.HEADLESS_CHROME == downerType) {
			downer = new HeadlessChromeDowner(worker, downloadTimeout, useAgent, proxy);
		}else {
			downer = new OkHttpDowner(worker, downloadTimeout, useAgent, proxy);
		}
		return downer;
	}

	@FunctionalInterface
	interface NewDownloadMethod {
		AbstractDowner newDownloader(String useAgent, HttpProxy proxy);
	}

//	private AbstractDowner returnAndGetDowner(AbstractDowner returnDownloader, String useAgent,
//			Map<HttpProxy, AbstractDowner> downloaders, HttpProxy proxy, NewDownloadMethod newDownload) {
//		synchronized (downloaders) {
//			AbstractDowner getDowner = downloaders.get(proxy);
//			if (null == getDowner) {
//				do {
//					for (AbstractDowner downer : downloaders.values()) {
//						if (!downer.isHold()) {
//							getDowner = downer;
//							break;
//						}
//					}
//					if (null == getDowner && isFree()) {
//						getDowner = newDownload.newDownloader(useAgent, proxy);
//						downloaders.put(proxy, getDowner);
//						//notifyCheckThread();
//					}
//				} while (null == getDowner);
//				if (null != returnDownloader) {
//					returnDownloader.release();
//				}
//			}
//			getDowner.hold();
//			return getDowner;
//		}
//
//	}

//	private boolean isFree() {
//		return false;
//	}

}
