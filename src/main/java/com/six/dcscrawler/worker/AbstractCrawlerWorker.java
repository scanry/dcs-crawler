package com.six.dcscrawler.worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.six.dcsjob.Job;
import com.six.dcsjob.JobSnapshot;
import com.six.dcsjob.JobSnapshotStatus;
import com.six.dcsjob.work.AbstractWorker;
import com.six.dcsjob.work.exception.WorkerException;

import com.six.dcscrawler.downer.Downer;
import com.six.dcscrawler.downer.DownerManager;
import com.six.dcscrawler.downer.DownerType;
import com.six.dcscrawler.downer.exception.HttpStatus202DownloadException;
import com.six.dcscrawler.downer.exception.HttpStatus403DownloadException;
import com.six.dcscrawler.downer.exception.HttpStatus404DownloadException;
import com.six.dcscrawler.downer.exception.HttpStatus502DownloadException;
import com.six.dcscrawler.downer.exception.HttpStatus503DownloadException;
import com.six.dcscrawler.downer.exception.HttpStatus504DownloadException;
import com.six.dcscrawler.utils.RobotsUtils;
import com.six.dcscrawler.worker.exception.CrawlException;
import com.six.dcscrawler.worker.exception.FoundIdentifyingCodeCrawlException;
import com.six.dcscrawler.worker.exception.UnknowCrawlException;

import crawlercommons.robots.BaseRobotRules;

import com.six.dcscrawler.extracter.Extracter;
import com.six.dcscrawler.fifter.PageFilter;
import com.six.dcscrawler.modle.CrawlParameters;
import com.six.dcscrawler.modle.ExtractItem;
import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.modle.Page;
import com.six.dcscrawler.modle.Site;
import com.six.dcscrawler.proxy.HttpProxyPool;
import com.six.dcscrawler.proxy.HttpProxyPoolFactory;
import com.six.dcscrawler.proxy.HttpProxyType;
import com.six.dcscrawler.storage.Storage;
import com.six.dcscrawler.storage.StorageFactory;
import com.six.dcscrawler.urlfetcher.UrlFetcher;

/**
 * @author liusong
 * @date 2017年8月11日
 * @email 359852326@qq.com
 */
public abstract class AbstractCrawlerWorker extends AbstractWorker<Page, CrawlWorkerSnapshot> {

	final static Logger log = LoggerFactory.getLogger(AbstractCrawlerWorker.class);

	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/*** 系统默认 采集数据id:由worker name +worker计数生成 ，跟业务无关 */
	public static final String DEFAULT_RESULT_ID = "id";
	/*** 系统默认 采集日期 字段 */
	public static final String DEFAULT_RESULT_COLLECTION_DATE = "collectionDate";
	/*** 系统默认 数据源url 字段 */
	public static final String DEFAULT_RESULT_ORIGIN_URL = "originUrl";
	/** 引用URL */
	public static final String DEFAULT_REAULT_REFERER_URL = "refererUrl";
	/** 下载超时 **/
	public static final int DOWNLOAD_TIMEOUT = 6000;
	/** 查找元素超时 **/
	public static final int FIND_ELEMENT_TIMEOUT = 1000;
	/** 页面处理失败，默认最大重试次数 **/
	public static final int PAGE_MAX_RETRY_COUNT = 3;
	/** 默认验证码识别空数组 **/
	private final static String[] DEFAULT_IDENTIFYING_CODE_CSS_ARRAY = new String[0];
	/** 默认结果存储 **/
	private final static String DEFAULT_STORAGE_CLASS = "com.six.dcscrawler.storage";
	/** 系统默认字段 **/
	public static final Set<String> DEFAULT_RESULT_KEY_SET = new HashSet<String>() {
		private static final long serialVersionUID = -5572845760795261691L;
		{
			DEFAULT_RESULT_KEY_SET.add(DEFAULT_RESULT_ID);
			DEFAULT_RESULT_KEY_SET.add(DEFAULT_RESULT_COLLECTION_DATE);
			DEFAULT_RESULT_KEY_SET.add(DEFAULT_RESULT_ORIGIN_URL);
			DEFAULT_RESULT_KEY_SET.add(DEFAULT_REAULT_REFERER_URL);
		}
	};
	/** 目标站点 **/
	private Site site;
	/** 下载器 **/
	private Downer downer;
	/** 页面缓存 **/
	private PageCache pageCache = PageCache.DEFAULT_INSTANCE;
	/** 抽取器 **/
	private Extracter extracter;
	/** 结果存储器 **/
	private Storage storage;
	/** robots协议规则 **/
	protected BaseRobotRules baseRobotRules;
	/** url挑选 **/
	private UrlFetcher urlFetcher;
	/** 页面过滤器 **/
	private PageFilter pageFilter;
	/** ip代理 **/
	private HttpProxyPool httpProxyPool;
	/** 是否去重 **/
	private boolean isDoDuplication;
	/** 最小延迟处理数据频率 **/
	protected long minWorkFrequency;
	/** 最大延迟处理数据频率 **/
	protected long maxWorkFrequency;
	/** 随机对象 产生随机控制时间 **/
	private static Random randomDownSleep = new Random();
	/** 抽取基准字段 **/
	private String mainResultKey;
	/** 抽取结果索引 **/
	private AtomicInteger extracterIndex = new AtomicInteger(0);
	/** 抽取项集合 **/
	private List<ExtractItem> extractItems;
	/** 获取元素超时 **/
	protected int findElementTimeout = FIND_ELEMENT_TIMEOUT;
	/** ip访问站点频率 **/
	private long ipVisitSiteInterval;
	/** 最大重试次数 **/
	private int pageRetryMaxCount = PAGE_MAX_RETRY_COUNT;
	/** 页面过滤器 **/
	private DownerType downerType;
	/** 打开下载缓冲 **/
	private boolean openDownCache;
	/** 使用下载缓冲 **/
	private boolean useDownCache;
	/** useAgent **/
	private String useAgent;
	/** 下载超时 **/
	private long downloadTimeout;
	/** 验证码css 数组 **/
	private String[] identifyingCodeCssArray = DEFAULT_IDENTIFYING_CODE_CSS_ARRAY;
	/** 下一页抽取 **/
	private ExtractItem nextPageExtractItem;
	/** 登录页面 **/
	private Page loginPage;

	public AbstractCrawlerWorker(Job job) {
		super(job);
	}

	@Override
	protected void initWorker() {
		boolean allowForbidden = getJob().getParamBoolean(CrawlParameters.ALLOW_FORBIDDEN, false);
		boolean robotsEnable = getJob().getParamBoolean(CrawlParameters.ROBOTS_ENABLE, false);
		baseRobotRules = RobotsUtils.getRobotRules(robotsEnable, getSite().getHomePage(), allowForbidden);
		if (BaseRobotRules.UNSET_CRAWL_DELAY != baseRobotRules.getCrawlDelay()) {
			minWorkFrequency = baseRobotRules.getCrawlDelay();
		} else {
			minWorkFrequency = getJob().getParamInt(CrawlParameters.VISIT_INTERVAL);
		}
		maxWorkFrequency = 2 * minWorkFrequency;
		isDoDuplication = getJob().getParamBoolean(CrawlParameters.IS_DO_DUPLICATION, false);

		pageRetryMaxCount = getJob().getParamInt(CrawlParameters.PAGE_RETRY_MAX_COUNT, PAGE_MAX_RETRY_COUNT);
		// 初始化 站点code
		site = (Site) getJob().getParam(CrawlParameters.SITE);
		// 初始化下载器
		downloadTimeout = getJob().getParamInt(CrawlParameters.DOWNLOAD_TIMEOUT, DOWNLOAD_TIMEOUT);
		useAgent = getJob().getParam(CrawlParameters.USER_AGENT, null);
		int httpProxyTypeInt = getJob().getParamInt(CrawlParameters.HTTP_PROXY_TYPE, 0);
		HttpProxyType httpProxyType = HttpProxyType.valueOf(httpProxyTypeInt);
		httpProxyPool = HttpProxyPoolFactory.buildHttpProxyPool(httpProxyType);
		openDownCache = getJob().getParamBoolean(CrawlParameters.OPEN_DOWN_CACHE, false);
		useDownCache = getJob().getParamBoolean(CrawlParameters.USE_DOWN_CACHE, false);
		if (openDownCache || useDownCache) {
			pageCache = (PageCache) getJob().getParam(CrawlParameters.PAGE_CACHE_IMPL);
		}
		int downerTypeInt = getJob().getParamInt(CrawlParameters.DOWNER_TYPE, 1);
		downerType = DownerType.valueOf(downerTypeInt);
		downer = DownerManager.getInstance().newDowner(this, (int) downloadTimeout, downerType, useAgent, nextProxy());
		// 初始化内容抽取
		String identifyingCodeCssArrayTemp = getJob().getParamStr(CrawlParameters.IDENTIFYING_CODE_CSS_ARRAY);
		if (StringUtils.isNotBlank(identifyingCodeCssArrayTemp)) {
			identifyingCodeCssArray = StringUtils.split(identifyingCodeCssArrayTemp, ";");
		}
		extractItems = getJob().getParam(CrawlParameters.EXTRACT_ITEMS);

		// 初始化数据存储
		String storageClass = getJob().getParam(CrawlParameters.RESULT_STORAGE_CLASS, DEFAULT_STORAGE_CLASS);
		this.storage = StorageFactory.newStore(this, storageClass);

		JobSnapshot jobSnapshot = getJobSpace().getJobSnapshot();
		if (JobSnapshotStatus.INIT == jobSnapshot.getStatus()) {
			initJob();
			jobSnapshot.setStatus(JobSnapshotStatus.EXECUTING);
			getJobSpace().updateJobSnapshot(jobSnapshot);
		}
		doLoginPage();
		initSelf();
	}

	@Override
	protected void process(Page page) throws WorkerException {
		if (baseRobotRules.isAllowed(page.getUrl())) {
			log.info("start to process page:" + page.toString());
			long downTime = 0;
			long extractTime = 0;
			long storeTime = 0;
			try {
				beforeDownload(page);
				long startTime = System.currentTimeMillis();
				downer.download(page);
				long endTime = System.currentTimeMillis();
				downTime = endTime - startTime;

				checkIdentifyingCode(page);

				startTime = System.currentTimeMillis();
				beforeExtract(page);
				Map<String, List<String>> extractResults = extracter.extract(page);
				endTime = System.currentTimeMillis();
				extractTime = endTime - startTime;

				urlFetcher.fetch(page, extractResults);
				beforeStore(page, extractResults);
				List<Map<String, String>> results = assemble(page, extractResults);

				startTime = System.currentTimeMillis();
				storage.store(results);
				endTime = System.currentTimeMillis();
				storeTime = endTime - startTime;

				Page nextPage = getNextPage(page, extractResults);
				getJobSpace().push(nextPage);
				if (isDoDuplication) {
					pageFilter.addRecord(page.toString());
				}
			} catch (CrawlException crawlerException) {
				crawlerException.addSuppressed(new RuntimeException("crawler process:" + page.toString()));
				throw crawlerException;
			} catch (Exception e) {
				throw new UnknowCrawlException("unknow crawler process:" + page.toString(), e);
			}
			log.info("finished processing,down time[" + downTime + "],extract time[" + extractTime + "],store time["
					+ storeTime + "]:" + page.toString());
			frequencyControl();
		} else {
			// TODO 记录这个page不允许被访问
		}
	}

	private void doLoginPage() {
		if (null != loginPage) {

		}
	}

	/**
	 * 频率控制
	 */
	protected void frequencyControl() {
		long sleep = System.currentTimeMillis() - getLastActivityTime();
		if (sleep < minWorkFrequency) {
			// 生成隨機 時間 避免服務器 識別出固定規律
			long tempTime = (long) (randomDownSleep.nextDouble() * maxWorkFrequency) + sleep;
			try {
				Thread.sleep(tempTime);
			} catch (InterruptedException e) {
			}
		}
	}

	private HttpProxy nextProxy() {
		HttpProxy proxy = httpProxyPool.getHttpProxy(getSite().getHomePage(), ipVisitSiteInterval);
		return proxy;
	}

	private void checkIdentifyingCode(Page page) {
		if (null != identifyingCodeCssArray) {
			for (String IdentifyingCodeCss : identifyingCodeCssArray) {
				if (!page.getDocument().select(IdentifyingCodeCss).isEmpty() && !doIdentifyingCode(page)) {
					throw new FoundIdentifyingCodeCrawlException();
				}
			}
		}
	}

	private List<Map<String, String>> assemble(Page page, Map<String, List<String>> extractResults) {
		List<Map<String, String>> assembleResult = null;
		List<String> mainResult = extractResults.get(mainResultKey);
		int primaryResultSize = null != mainResult ? mainResult.size() : 0;
		if (primaryResultSize > 0) {
			assembleResult = new ArrayList<>(primaryResultSize);
			String nowTime = DateFormatUtils.format(System.currentTimeMillis(), DATE_FORMAT);
			List<String> idList = new ArrayList<>(primaryResultSize);
			List<String> collectionDateList = new ArrayList<>(primaryResultSize);
			List<String> originUrlList = new ArrayList<>(primaryResultSize);
			String id = null;
			for (int i = 0; i < primaryResultSize; i++) {
				Map<String, String> dataMap = new HashMap<>();
				for (ExtractItem extractItem : extractItems) {
					List<String> tempResultList = extractResults.get(extractItem.getOutputKey());
					String result = tempResultList.get(i);
					dataMap.put(extractItem.getOutputKey(), result);
				}
				id = getName() + "_" + extracterIndex.getAndIncrement();
				dataMap.put(DEFAULT_RESULT_ID, id);
				dataMap.put(DEFAULT_RESULT_COLLECTION_DATE, nowTime);
				dataMap.put(DEFAULT_RESULT_ORIGIN_URL, page.getUrl());
				dataMap.put(DEFAULT_REAULT_REFERER_URL, page.getReferer());

				idList.add(id);
				collectionDateList.add(nowTime);
				originUrlList.add(page.toString());
				assembleResult.add(dataMap);
			}
		} else {
			assembleResult = Collections.emptyList();
		}
		return assembleResult;
	}

	protected Page getNextPage(Page page, Map<String, List<String>> extractResults) {
		if (null != nextPageExtractItem) {

		}
		return null;
	}

	@Override
	protected void onError(Exception e, Page page) {
		if (null != page) {
			if (e instanceof HttpStatus404DownloadException) {
				getJobSpace().addErr(page);
				getJobSpace().ack(page);
			} else {
				Exception insideException = null;
				boolean insideExceptionResult = false;
				// 异常先丢给实现类自己处理
				try {
					insideExceptionResult = insideOnError(e, page);
				} catch (Exception e1) {
					insideException = e1;
					log.error("insideOnError err page:" + page.getFinalUrl(), e1);
				}
				// 判断内部处理是否可处理,如果不可处理那么这里默认处理
				if (insideExceptionResult) {
					getJobSpace().ack(page);
				} else {
					if (e instanceof HttpStatus502DownloadException || e instanceof HttpStatus503DownloadException
							|| e instanceof HttpStatus504DownloadException) {
						long restTime = 1000 * 5;
						log.info("perhaps server is too busy,it's time for having a rest(" + restTime + ")");
						try {
							Thread.sleep(restTime);
						} catch (InterruptedException e1) {
						}
					}
					if (e instanceof FoundIdentifyingCodeCrawlException || e instanceof HttpStatus202DownloadException
							|| e instanceof HttpStatus403DownloadException) {
						// TODO 可以切换ip
					}
					String msg = null;
					if (null == insideException && page.getRetryCount() < pageRetryMaxCount) {
						page.setRetryCount(page.getRetryCount() + 1);
						// TODO 重新处理
						msg = "retry processor[" + page.getRetryCount() + "] page:" + page.getFinalUrl();
					} else {
						getJobSpace().addErr(page);
						getJobSpace().ack(page);
						msg = "retry process count[" + page.getRetryCount() + "]>=" + pageRetryMaxCount
								+ " and push to err queue:" + page.getFinalUrl();

					}
					log.error(msg, e);
				}
			}
		}
	}

	@Override
	protected void insideDestroy() {
		close(downer);
		close(pageCache);
		close(httpProxyPool);
		close(storage);
	}

	private void close(AutoCloseable autoCloseable) {
		if (null != autoCloseable) {
			try {
				autoCloseable.close();
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}

	protected final Site getSite() {
		return site;
	}

	protected final PageFilter getPageFilter() {
		return pageFilter;
	}

	/**
	 * work自身初始化
	 */
	protected abstract void initSelf();

	protected abstract void beforeDownload(Page page);

	protected abstract void beforeExtract(Page page);

	protected abstract void beforeStore(Page page, Map<String, List<String>> extractResults);

	protected boolean doIdentifyingCode(Page page) {
		return false;
	}

	/**
	 * 内部异常处理，如果成功处理返回true 否则返回false;
	 * 
	 * @param e
	 * @param doingPage
	 * @return
	 */
	protected boolean insideOnError(Exception e, Page doingPage) {
		return false;
	}

}
