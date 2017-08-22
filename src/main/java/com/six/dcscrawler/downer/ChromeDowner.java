package com.six.dcscrawler.downer;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.six.dcscrawler.downer.exception.OtherDownerException;
import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.modle.Page;
import com.six.dcscrawler.worker.AbstractCrawlerWorker;

/**   
* @author liusong  
* @date   2017年8月17日 
* @email  359852326@qq.com 
*/
public class ChromeDowner extends AbstractDowner{


	protected final static Logger LOG = LoggerFactory.getLogger(ChromeDowner.class);

	private ChromeDriver browser;

	private static final long defaultWaitTimeOunt = 3000;

	private Executor executor = Executors.newSingleThreadExecutor();
	
	private Thread processThread;

	private static String getChromeProxyExtension(String path, String proxy) throws IOException {
		String chromeProxyExtensionsDir = path + File.separator + "chrome-proxy-extensions";
		String usernameAndPwd = StringUtils.substringBefore(proxy, "@");
		String username = StringUtils.substringBefore(usernameAndPwd, ":");
		String password = StringUtils.substringAfter(usernameAndPwd, ":");
		String ipAndPort = StringUtils.substringAfter(proxy, "@");
		String ip = StringUtils.substringBefore(ipAndPort, ":");
		String port = StringUtils.substringAfter(ipAndPort, ":");
		File extensionFileDir = FileUtils.getFile(chromeProxyExtensionsDir);
		if (!extensionFileDir.exists()) {
			extensionFileDir.mkdirs();
		}
		String extensionFilePath = chromeProxyExtensionsDir + File.separator + proxy.replace(':', '_') + ".zip";
		File extensionFile = FileUtils.getFile(chromeProxyExtensionsDir);
		if (extensionFile.exists() ? extensionFile.delete() : false) {
		}

		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(new File(extensionFilePath)));

		String manifestPath = path + File.separator + "manifest.json";
		String manifest = FileUtils.readFileToString(FileUtils.getFile(manifestPath));
		String backgroundContentPath = path + File.separator + "background.js";
		String backgroundContent = FileUtils.readFileToString(FileUtils.getFile(backgroundContentPath));
		backgroundContent = backgroundContent.replace("%proxy_host", ip);
		backgroundContent = backgroundContent.replace("%proxy_port", port);
		backgroundContent = backgroundContent.replace("%username", username);
		backgroundContent = backgroundContent.replace("%password", password);

		ZipEntry manifestZipEntry = new ZipEntry("manifest.json");
		zipOut.putNextEntry(manifestZipEntry);
		zipOut.write(manifest.getBytes());

		ZipEntry backgroundZipEntry = new ZipEntry("background.js");
		zipOut.putNextEntry(backgroundZipEntry);
		zipOut.write(backgroundContent.getBytes());
		zipOut.close();
		return extensionFilePath;
	}

	public ChromeDowner(AbstractCrawlerWorker worker,int downloadTimeout,String useAgent,HttpProxy proxy){ 
		super(worker, downloadTimeout, useAgent, proxy);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		HashMap<String, Object> settings = new HashMap<String, Object>();
		String loadImages ="1";
		if ("0".equals(loadImages)) {
			settings.put("images", 2); // 设置不加载图片
		}
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.managed_default_content_settings", settings);
		ChromeOptions options = new ChromeOptions();
		
		// String chromeUserDataFileName = chromePath + File.separatorChar +
		// "usrData" + File.separatorChar
		// + worker.getName();
		// options.addArguments("user-data-dir=" + chromeUserDataFileName);
		//options.addArguments("--headless");
		options.setExperimentalOption("prefs", prefs);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		if (null != proxy) {
			if (StringUtils.isNotBlank(proxy.getUserName())) {
				try {
					String proxyExtensionPath = getChromeProxyExtension("", proxy.toStringUserAndpwd());
					options.addExtensions(new File(proxyExtensionPath));
				} catch (IOException e) {
					throw new OtherDownerException("chrome set proxy username err");
				}
			} else {
				Proxy seleProxy = new Proxy();
				seleProxy.setHttpProxy(proxy.toString());
				cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
				cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
				cap.setCapability(CapabilityType.PROXY, proxy);
			}
		}
		browser = new ChromeDriver(cap);
		// 搜索元素时 10秒异常
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// 页面加载 30秒抛异常
		browser.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		browser.manage().timeouts().setScriptTimeout(1000, TimeUnit.MILLISECONDS);
	}

	@Override
	protected void insideDownload(Page page) {
		final CountDownLatch latch = new CountDownLatch(1);
		executor.execute(() -> {
			processThread=Thread.currentThread();
			try {
				browser.get(page.getUrl());
			} finally {
				latch.countDown();
			}
		});
		try {
			latch.await(defaultWaitTimeOunt, MILLISECONDS);
		} catch (InterruptedException e) {}
		String html = browser.getPageSource();
		String currentUrl = browser.getCurrentUrl();
		page.setContent(html);
		page.setFinalUrl(currentUrl);
	}

	public WebDriver getWebDriver() {
		return browser;
	}

	public String getWindowHandle() {
		return browser.getWindowHandle();
	}

	public WebElement findWebElement(String xpath) {
		return browser.findElementByXPath(xpath);
	}

	public List<WebElement> findWebElements(String xpath) {
		return browser.findElementsByXPath(xpath);
	}

	public void click(WebElement webElement, String xpath) {
		webElement.click();
	}

	protected void waitForload() {}

	protected boolean internalLoginAndLogout(Page page) {
		return false;
	}

	protected boolean checkIsLogOut(Page page) {
		return false;
	}

	@Override
	public void clearCookie() {
		browser.manage().deleteAllCookies();
	}

	@Override
	protected void doSetProxy(HttpProxy proxy) {
		
	}

	public void close() {
		processThread.interrupt();
		browser.close();
		browser.quit();
	}


}
