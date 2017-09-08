package com.six.dcscrawler.downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.six.dcscrawler.modle.HttpProxy;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2016年10月14日 上午10:53:52
 */
public class DownloaderFactory {

	final static Logger log = LoggerFactory.getLogger(DownloaderFactory.class);

	// private Map<HttpProxy, AbstractDowner> jsDownloaders = new
	// LinkedHashMap<>();
	// private Map<HttpProxy, AbstractDowner> noJsdownloaders = new
	// LinkedHashMap<>();
	// private Thread checkLastActivityTimeThread;
	// private Object checkLastActivityTimeThreadObject = new Object();
	// private long maxActivityInterval = 1000 * 60 * 5;

	private DownloaderFactory() {
		// Runtime.getRuntime().addShutdownHook(new Thread(() -> {
		// for(AbstractDowner downloader:downloaders){
		// try {
		// downloader.close();
		// } catch (Exception e) {
		// log.error("", e);
		// }
		// }
		// downloaders.clear();
		// }, "close-all-downloader-thread"));
		// checkLastActivityTimeThread = new Thread(() -> {
		// while (true) {
		// try {
		// checkLastActivityTime(jsDownloaders);
		// checkLastActivityTime(noJsdownloaders);
		// } catch (Exception e) {
		// log.error("", e);
		// }
		// synchronized (checkLastActivityTimeThreadObject) {
		// try {
		// checkLastActivityTimeThreadObject.wait(maxActivityInterval);
		// } catch (InterruptedException e) {
		// }
		// }
		// }
		// }, "check-downloader-lastActivityTime-thread");
		// checkLastActivityTimeThread.setDaemon(true);
		// checkLastActivityTimeThread.start();
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static DownloaderFactory getInstance() {
		return DownerManagerUtils.downloaderFactory;
	}

	/**
	 * 单例模式 实现赖加载
	 * 
	 * @author six
	 * @email 359852326@qq.com
	 */
	private static class DownerManagerUtils {
		static DownloaderFactory downloaderFactory = new DownloaderFactory();
	}

	// private void checkLastActivityTime(Map<HttpProxy, AbstractDowner>
	// downloaders) {
	// Iterator<AbstractDowner> jsIterator = downloaders.values().iterator();
	// AbstractDowner downloader = null;
	// while (jsIterator.hasNext()) {
	// downloader = jsIterator.next();
	// if (!downloader.isHold() && downloader.lastActivityTime() >=
	// maxActivityInterval) {
	// downloader.close();
	// jsIterator.remove();
	// }
	// }
	// }

	// private void notifyCheckThread() {
	// synchronized (checkLastActivityTimeThreadObject) {
	// checkLastActivityTimeThreadObject.notifyAll();
	// }
	// }

	// public AbstractDowner returnAndGetDowner(AbstractCrawlWorker worker, int
	// downloadTimeout, DownerType downerType,
	// String useAgent, AbstractDowner returnDownloader, HttpProxy proxy) {
	// if (DownerType.CHROME == downerType) {
	// return returnAndGetDowner(returnDownloader, useAgent, jsDownloaders,
	// proxy, (useAgentP, proxyP) -> {
	// AbstractDowner downer = new ChromeDowner(worker, downloadTimeout,
	// useAgentP, proxyP);
	// return downer;
	// });
	// } else {
	// return returnAndGetDowner(returnDownloader, useAgent, noJsdownloaders,
	// proxy, (useAgentP, proxyP) -> {
	// AbstractDowner downer = new OkHttpDowner(worker, downloadTimeout,
	// useAgentP, proxyP);
	// return downer;
	// });
	// }
	// }

	public AbstractDownloader newDowner(String downloaderPath,DownloaderType downerType, int downloadTimeout,
			String useAgent, HttpProxy proxy,boolean headless,boolean isLoadImages) {
		AbstractDownloader downer = null;
		if (DownloaderType.CHROME == downerType) {
			downer = new ChromedriverDowner(downloaderPath, downloadTimeout, useAgent, proxy, headless,isLoadImages);
		} else if (DownloaderType.HEADLESS_CHROME == downerType) {
			downer = new ChromeCd4jDownloader(downloaderPath, downloadTimeout, useAgent, proxy, headless);
		} else {
			downer = new OkHttpDownloader(downloaderPath, downloadTimeout, useAgent, proxy);
		}
		return downer;
	}

	@FunctionalInterface
	interface NewDownloadMethod {
		AbstractDownloader newDownloader(String useAgent, HttpProxy proxy);
	}

	// private AbstractDowner returnAndGetDowner(AbstractDowner
	// returnDownloader, String useAgent,
	// Map<HttpProxy, AbstractDowner> downloaders, HttpProxy proxy,
	// NewDownloadMethod newDownload) {
	// synchronized (downloaders) {
	// AbstractDowner getDowner = downloaders.get(proxy);
	// if (null == getDowner) {
	// do {
	// for (AbstractDowner downer : downloaders.values()) {
	// if (!downer.isHold()) {
	// getDowner = downer;
	// break;
	// }
	// }
	// if (null == getDowner && isFree()) {
	// getDowner = newDownload.newDownloader(useAgent, proxy);
	// downloaders.put(proxy, getDowner);
	// //notifyCheckThread();
	// }
	// } while (null == getDowner);
	// if (null != returnDownloader) {
	// returnDownloader.release();
	// }
	// }
	// getDowner.hold();
	// return getDowner;
	// }
	//
	// }

	// private boolean isFree() {
	// return false;
	// }

}
