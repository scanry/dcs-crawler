package com.six.dcscrawler.downer;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.modle.Page;
import com.six.dcscrawler.worker.AbstractCrawlerWorker;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.command.Network;
import io.webfolder.cdp.logger.CdpLoggerType;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

/**
 * @author liusong
 * @date 2017年8月7日
 * @email 359852326@qq.com
 */
public class HeadlessChromeDowner extends AbstractDowner {

	protected final static Logger LOG = LoggerFactory.getLogger(HeadlessChromeDowner.class);

	private Launcher launcher;
	
	private SessionFactory factory;
	
	private Session session;
	

	public HeadlessChromeDowner(AbstractCrawlerWorker worker,int downloadTimeout,String useAgent,HttpProxy proxy) {
		super(worker,downloadTimeout,useAgent,proxy);
		newChrome(proxy);
	}
	
	private void newChrome(HttpProxy proxy){
		factory=new SessionFactory(getFreePort(),CdpLoggerType.Null);
		launcher = new Launcher(factory);
		List<String> list = new ArrayList<>();
		list.add("--headless");
		list.add("--disable-gpu");
		list.add("--enable-javascript");
		if(null!=proxy){
			list.add("--proxy-server=" + proxy.getHost() + ":" + proxy.getPort());
		}
		launcher.launch(list);
		session = factory.create();
		if(null!=proxy){
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
	private static int getFreePort(){
		ServerSocket serverSocket=null;
		try {
			serverSocket = new ServerSocket(0);// 读取空闲的可用端口
			int port = serverSocket.getLocalPort();
			return port;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally{
			if(null!=serverSocket){
				try {
					serverSocket.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	protected void doSetProxy(HttpProxy proxy){
		
	}
	
	@Override
	protected void insideDownload(final Page page){
		session.navigate(page.getUrl());
		try{
			Thread.sleep(1000);
			session.waitDocumentReady(getDownloadTimeout());
		}catch (Exception e) {
			log.error("", e);
		}
		try{
			String content = (String) session.getProperty("//html", "outerHTML");
			page.setHttpCode(200);
			page.setContent(content);
			page.setTitle(session.getTitle());
		}catch (Exception e) {
			log.error("", e);
		}
	}

	public WebDriver getWebDriver() {
		return null;
	}

	public String getWindowHandle() {
		return null;
	}

	public WebElement findWebElement(String xpath) {
		return null;
	}

	public List<WebElement> findWebElements(String xpath) {
		return null;
	}

	public void click(WebElement webElement, String xpath) {

	}

	protected boolean internalLoginAndLogout(Page page) {
		return false;
	}

	protected boolean checkIsLogOut(Page page) {
		return false;
	}

	@Override
	public void clearCookie() {
		session.clearCache();
		session.clearCookies();
	}

	public void close() {
		if(null!=session){
			try{
				session.close();
			}catch (Exception e) {}
		}
		if(null!=session){
			try{
				factory.close();
			}catch (Exception e) {}
		}
		if(null!=launcher){
			launcher.close();
		}
	}
}
