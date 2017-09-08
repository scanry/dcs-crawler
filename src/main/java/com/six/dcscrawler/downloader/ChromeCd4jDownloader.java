package com.six.dcscrawler.downloader;

import static io.webfolder.cdp.event.Events.NetworkResponseReceived;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.modle.Page;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.command.Network;
import io.webfolder.cdp.event.network.ResponseReceived;
import io.webfolder.cdp.logger.CdpLoggerType;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.type.network.Response;

/**
 * @author liusong
 * @date 2017年8月7日
 * @email 359852326@qq.com
 */
public class ChromeCd4jDownloader extends AbstractDownloader {

	protected final static Logger LOG = LoggerFactory.getLogger(ChromeCd4jDownloader.class);

	private Launcher launcher;

	private SessionFactory factory;

	private Session session;
	
	private boolean headless;

	public ChromeCd4jDownloader(String downloaderPath, 
			int downloadTimeout, String useAgent, HttpProxy proxy,boolean headless) {
		super(downloaderPath, downloadTimeout, useAgent, proxy);
		this.headless=headless;
		newChrome(proxy);
	}

	private void newChrome(HttpProxy proxy) {
		factory = new SessionFactory(getFreePort(), CdpLoggerType.Null);
		launcher = new Launcher(factory);
		List<String> list = new ArrayList<>();
		if (headless) {
			list.add("--headless");
			list.add("--disable-gpu");
		}
		list.add("--enable-javascript");
		if (null != proxy) {
			list.add("--proxy-server=" + proxy.getHost() + ":" + proxy.getPort());
		}
		launcher.launch(list);
		session = factory.create();
		if (null != proxy) {
			Network network = session.getCommand().getNetwork();
			String nameAndPass = proxy.getUserName() + ":" + proxy.getPassWord();
			String encoding = new String(Base64.getEncoder().encode(nameAndPass.getBytes()));
			Map<String, Object> headers = Maps.newHashMap();
			headers.put("Proxy-Authorization", "Basic " + encoding);
			network.setExtraHTTPHeaders(headers);
			network.enable();
		}
		clearCookie();
	}

	private static int getFreePort() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(0);// 读取空闲的可用端口
			int port = serverSocket.getLocalPort();
			return port;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (null != serverSocket) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	protected void doSetProxy(HttpProxy proxy) {

	}

	@Override
	protected void insideDownload(final Page page) {

		final CountDownLatch cdl = new CountDownLatch(1);
		session.navigate(page.getUrl());
		session.addEventListener((e, d) -> {
			if (NetworkResponseReceived.equals(e)) {
				try {
					ResponseReceived rr = (ResponseReceived) d;
					Response response = rr.getResponse();
					page.setFinalUrl(response.getUrl());
					page.setHttpCode(response.getStatus().intValue());
				} finally {
					cdl.countDown();
				}
			}
		});
		try {
			cdl.await();
			session.waitDocumentReady((int)getDownloadTimeout());
		} catch (Exception e) {
			log.error("", e);
		}
		try {
			String html = (String) session.getProperty("//html", "outerHTML");
			page.setContent(html);
			page.setTitle(session.getTitle());
		} catch (Exception e) {
			log.error("", e);
		}
	}
	

	@Override
	public ChromeDriver getChromeDriver() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValue(String selector, String value) {
		session.focus(selector).sendKeys(value);
		sleep();
	}

	@Override
	public void click(String selector) {
		session.click(selector);
		sleep();
	}
	
	private static void sleep(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
	}

	@Override
	public void clearCookie() {
		session.clearCache();
		session.clearCookies();
	}

	public void close() {
		if (null != session) {
			try {
				session.close();
			} catch (Exception e) {
			}
		}
		if (null != session) {
			try {
				factory.close();
			} catch (Exception e) {
			}
		}
		if (null != launcher) {
			launcher.close();
		}
	}
}
