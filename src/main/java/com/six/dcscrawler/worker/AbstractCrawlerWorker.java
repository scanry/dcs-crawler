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

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.six.dcsjob.work.AbstractWorker;
import com.six.dcsjob.work.exception.WorkerException;

import com.six.dcscrawler.downer.Downer;
import com.six.dcscrawler.utils.RobotsUtils;

import crawlercommons.robots.BaseRobotRules;

import com.six.dcscrawler.extracter.Extracter;
import com.six.dcscrawler.fifter.PageFilter;
import com.six.dcscrawler.modle.CrawlParameters;
import com.six.dcscrawler.modle.ExtractItem;
import com.six.dcscrawler.modle.Page;
import com.six.dcscrawler.modle.Site;
import com.six.dcscrawler.storage.Storage;
import com.six.dcscrawler.urlfetcher.UrlFetcher;

/**
 * @author liusong
 * @date 2017年8月11日
 * @email 359852326@qq.com
 */
public abstract class AbstractCrawlerWorker extends AbstractWorker<Page> {

	final static Logger log = LoggerFactory.getLogger(AbstractCrawlerWorker.class);
			
	final static String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	/*** 系统默认 采集数据id:由worker name +worker计数生成 ，跟业务无关*/
	public static final String DEFAULT_RESULT_ID = "id";	
	/*** 系统默认 采集日期 字段 */
	public static final String DEFAULT_RESULT_COLLECTION_DATE = "collectionDate";
	/*** 系统默认 数据源url 字段 */
	public static final String DEFAULT_RESULT_ORIGIN_URL = "originUrl";	
	/** * 引用URL */
	public static final String DEFAULT_REAULT_REFERER_URL="refererUrl";

	public static final Set<String> DEFAULT_RESULT_KEY_SET = new HashSet<String>() {
		private static final long serialVersionUID = -5572845760795261691L;
		{
			DEFAULT_RESULT_KEY_SET.add(DEFAULT_RESULT_ID);
			DEFAULT_RESULT_KEY_SET.add(DEFAULT_RESULT_COLLECTION_DATE);
			DEFAULT_RESULT_KEY_SET.add(DEFAULT_RESULT_ORIGIN_URL);
			DEFAULT_RESULT_KEY_SET.add(DEFAULT_REAULT_REFERER_URL);
		}
	};

	private Site site;

	private Downer downer;

	private Extracter extracter;

	private Storage storage;

	private UrlFetcher urlFetcher;
	
	private PageFilter pageFilter;

	protected BaseRobotRules baseRobotRules;
	private boolean isDeDuplication;
	// 最小延迟处理数据频率
	protected long minWorkFrequency;
	// 最大延迟处理数据频率
	protected long maxWorkFrequency;
	// 随机对象 产生随机控制时间
	private static Random randomDownSleep = new Random();
	
	private String mainResultKey;
	
	private AtomicInteger extracterIndex = new AtomicInteger(0);
	
	private List<ExtractItem> extractItems;

	@Override
	protected void initWorker() {
		boolean allowForbidden = getJob().getParamBoolean(CrawlParameters.ALLOW_FORBIDDEN, false);
		boolean robotsEnable = getJob().getParamBoolean(CrawlParameters.ROBOTS_ENABLE, false);
		baseRobotRules = RobotsUtils.getRobotRules(robotsEnable, getSite().getHomePage(), allowForbidden);
		if(BaseRobotRules.UNSET_CRAWL_DELAY!=baseRobotRules.getCrawlDelay()){
			minWorkFrequency =baseRobotRules.getCrawlDelay();
		}else{
			minWorkFrequency = getJob().getParamInt(CrawlParameters.VISIT_INTERVAL);
		}
		maxWorkFrequency = 2 * minWorkFrequency;
		isDeDuplication=getJob().getParamBoolean(CrawlParameters.IS_DE_DUPLICATION,false);
	}

	@Override
	protected void process(Page page) throws WorkerException {
		if (baseRobotRules.isAllowed(page.getUrl())) {
			beforeDownload(page);
			downer.download(page);
			beforeExtract(page);
			Map<String, List<String>> extractResults = extracter.extract(page);
			urlFetcher.fetch(page, extractResults);
			beforeStore(page, extractResults);
			List<Map<String, String>> results = assemble(page,extractResults);
			storage.store(results);
			Page nextPage=getNextPage(page, extractResults);
			getJobSpace().push(nextPage);
			if(isDeDuplication){
				pageFilter.addRecord(page.toString());
			}
			frequencyControl();
		} else {
			// TODO 记录这个page不允许被访问
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

	private List<Map<String, String>> assemble(Page page,Map<String, List<String>> extractResults) {
		List<Map<String, String>> assembleResult=null;
		List<String> mainResult= extractResults.get(mainResultKey);
		int primaryResultSize=null!=mainResult?mainResult.size():0;
		if (primaryResultSize > 0) {
			assembleResult=new ArrayList<>(primaryResultSize);
			String nowTime = DateFormatUtils.format(System.currentTimeMillis(),DATE_FORMAT);
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
		}else{
			assembleResult=Collections.emptyList();
		}
		return assembleResult;
	}
	
	protected Page getNextPage(Page page,Map<String, List<String>> extractResults){
		return null;
	}
	
	@Override
	protected void onError(Exception t,Page page) {

	}

	@Override
	protected void insideDestroy() {

	}

	protected final Site getSite() {
		return site;
	}
	
	protected final PageFilter getPageFilter(){
		return pageFilter;
	}
	
	/**
	 * work自身初始化
	 */
	protected abstract void initSelf();

	protected abstract void beforeDownload(Page page);

	protected abstract void beforeExtract(Page page);

	protected abstract void beforeStore(Page page, Map<String, List<String>> extractResults);

}
