package com.six.dcscrawler.downloader;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.awt.Toolkit;
import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.six.dcscrawler.downloader.exception.OtherDownloadException;
import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.modle.Page;

/**
 * @author six
 * @date 2016年5月18日 下午5:42:26
 */
public class ChromedriverDowner extends AbstractDownloader {

	protected final static Logger LOG = LoggerFactory.getLogger(ChromedriverDowner.class);

	private ChromeDriver browser;

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	private Thread processThread;
	
	private boolean headless;
	
	private boolean isLoadImages;

	public ChromedriverDowner(String downloaderPath, 
			long downloadTimeout, String useAgent, HttpProxy proxy,boolean headless,boolean isLoadImages) {
		super(downloaderPath, downloadTimeout, useAgent, proxy);
		this.headless=headless;
		this.isLoadImages=isLoadImages;
		browser=newChromeDriver(downloaderPath,downloadTimeout,proxy);
	}
	
	private ChromeDriver newChromeDriver(String downloaderPath,long downloadTimeout,HttpProxy proxy){
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		HashMap<String, Object> settings = new HashMap<String, Object>();
		if (!isLoadImages) {
			settings.put("images", 2); // 设置不加载图片
		}
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.managed_default_content_settings", settings);
		ChromeOptions options = new ChromeOptions();
		if(headless){
			options.addArguments("--headless");
			options.addArguments("--disable-gpu");	
		}
		options.setExperimentalOption("prefs", prefs);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		if (null != proxy) {
			if (StringUtils.isNotBlank(proxy.getUserName())) {
				String chromePath = downloaderPath + File.separatorChar + "chrome";
				try {
					String proxyExtensionPath = ChromedriverProxyUtils.getChromeProxyExtension(chromePath,String.valueOf(System.currentTimeMillis()) ,
							proxy.toStringUserAndpwd());
					options.addExtensions(new File(proxyExtensionPath));
				} catch (IOException e) {
					throw new OtherDownloadException("chrome set proxy username err");
				}
			} else {
				Proxy seleProxy = new Proxy();
				seleProxy.setHttpProxy(proxy.toString());
				cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
				cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
				cap.setCapability(CapabilityType.PROXY, proxy);
			}
		}
		ChromeDriver browser = new ChromeDriver(cap);
		Toolkit kit = Toolkit.getDefaultToolkit();
		java.awt.Dimension jdm=kit.getScreenSize();
		browser.manage().window().setSize(new Dimension((int)jdm.getWidth(), (int)jdm.getHeight()));
		// 搜索元素时 10秒异常
		browser.manage().timeouts().implicitlyWait(downloadTimeout*4, TimeUnit.MILLISECONDS);
		// 页面加载 30秒抛异常
		browser.manage().timeouts().pageLoadTimeout(downloadTimeout, TimeUnit.MILLISECONDS);
		browser.manage().timeouts().setScriptTimeout(downloadTimeout*2, TimeUnit.MILLISECONDS);
		browser.manage().deleteAllCookies();
		return browser;
	}
	
	@Override
	protected void insideDownload(final Page page) {
		final CountDownLatch latch = new CountDownLatch(1);
		log.info("chrome start download page:"+page.getUrl());
		executor.execute(() -> {
			processThread = Thread.currentThread();
			try {
				log.info("chrome start get");
				browser.get(page.getUrl());
			} catch (Exception e) {
				log.error("", e);
			} finally {
				log.info("chrome end get");
				latch.countDown();
			}
		});
		try {
			log.info("chrome start await");
			latch.await(getDownloadTimeout(), MILLISECONDS);
			log.info("chrome end await");
			page.setHttpCode(200);
			page.setTitle(browser.getTitle());
			page.setContent(browser.getPageSource());
			page.setFinalUrl(browser.getCurrentUrl());
		} catch (Exception e) {
			log.error("", e);
		}
	}
	

	@Override
	public ChromeDriver getChromeDriver() {
		return browser;
	}

	
	@Override
	public void setValue(String selector,String value) {
		WebElement element=browser.findElementByCssSelector(selector);
		if(null!=element){
			element.sendKeys(value);
			sleep(1000);
		}
	}

	
	@Override
	public void click(String selector) {
		WebElement element=browser.findElementByCssSelector(selector);
		if(null!=element){
			element.click();
			sleep(1000);
		}
	}


	@Override
	public void clearCookie() {
		browser.manage().deleteAllCookies();
	}



	@Override
	protected void doSetProxy(HttpProxy httpProxy) {
		browser.close();
		browser.quit();
		browser=newChromeDriver(getDownloaderPath(),getDownloadTimeout(),httpProxy);
	}

	@Override
	public void close() {
		if(null!=processThread){
			processThread.interrupt();
		}
		browser.close();
		browser.quit();
		if (null != executor) {
			executor.shutdown();
		}
	}
}
