package com.six.dcsjob.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import com.six.dcscrawler.downloader.AbstractDownloader;
import com.six.dcscrawler.downloader.DownloaderFactory;
import com.six.dcscrawler.downloader.DownloaderType;
import com.six.dcscrawler.modle.Page;
import com.six.dcscrawler.utils.WebDriverUtils;
import com.six.dcsjob.downer.ChromedriverDownerTest;

/**
 * @author liusong
 * @date 2017年9月6日
 * @email 359852326@qq.com 列表页URL自动分析
 */
public class ListPageAnalysisTest extends ChromedriverDownerTest {

	public static void main(String[] args) {
		//String exampleUrl = "http://blog.csdn.net/";
		//String exampleUrl ="https://www.oschina.net/blog";
		//String exampleUrl ="https://my.oschina.net/linker/blog/1529928";
		//String exampleUrl ="http://www.blogjava.net/";
		String exampleUrl ="https://sz.lianjia.com/ershoufang/";
		AbstractDownloader downer = null;
		Set<String> sectionSet = new HashSet<>();
		sectionSet.add("移动开发");
		sectionSet.add("编程语言");
		sectionSet.add("架构");
		sectionSet.add("云计算/大数据");
		sectionSet.add("前端");
		try {
			downer = DownloaderFactory.getInstance().newDowner(null, DownloaderType.CHROME, 6000, null, null, false,
					true);
			Page page = new Page();
			page.setUrl(exampleUrl);
			downer.download(page);
			List<WebElement> aElements = WebDriverUtils.findElements(downer.getChromeDriver(), "a");
			/** 样式 统计 **/
			Map<String, AtomicInteger> styleStatistics = new ConcurrentHashMap<String, AtomicInteger>();
			/** 位置统计（只考虑x坐标） 统计 **/
			Map<String, AtomicInteger> positionStatistics = new ConcurrentHashMap<String, AtomicInteger>();

			for (WebElement aElement : aElements) {
				// String cssValue = aElement.getAttribute("css");
				// styleStatistics.computeIfAbsent(cssValue, newKey -> {
				// return new AtomicInteger(0);
				// }).incrementAndGet();
				if (!sectionSet.contains(StringUtils.trim(aElement.getText())) 
						&& aElement.getLocation().x > 120
						&&aElement.getLocation().x <900
						&&aElement.getLocation().y >100) {
					String positionValue = aElement.getLocation().x + StringUtils.EMPTY;
					positionStatistics.computeIfAbsent(positionValue, newKey -> {
						return new AtomicInteger(0);
					}).incrementAndGet();
					System.out.println("position:" + positionValue);
					System.out.println("href:" + aElement.getAttribute("href"));
				}

			}
			System.out.println("--------------------------------------------------------------------------------");
			styleStatistics = sortMap(styleStatistics);
			positionStatistics = sortMap(positionStatistics);
			System.out.println("--------------------------------------------------------------------------------");
			for (String style : styleStatistics.keySet()) {
				System.out.println("style:" + style);
				System.out.println("total:" + styleStatistics.get(style));
			}
			System.out.println("--------------------------------------------------------------------------------");
			for (String position : positionStatistics.keySet()) {
				System.out.println("position:" + position);
				System.out.println("total:" + positionStatistics.get(position));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != downer) {
				downer.close();
			}
		}
	}

	public static Map<String, AtomicInteger> sortMap(Map<String, AtomicInteger> oldMap) {
		List<Map.Entry<String, AtomicInteger>> list = new ArrayList<>(oldMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, AtomicInteger>>() {
			@Override
			public int compare(Entry<String, AtomicInteger> arg0, Entry<String, AtomicInteger> arg1) {
				if (arg0.getValue().get() > arg1.getValue().get()) {
					return -1;
				} else if (arg0.getValue().get() < arg1.getValue().get()) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		Map<String, AtomicInteger> newMap = new LinkedHashMap<String, AtomicInteger>();
		for (int i = 0; i < list.size(); i++) {
			newMap.put(list.get(i).getKey(), list.get(i).getValue());
		}
		return newMap;
	}

}
