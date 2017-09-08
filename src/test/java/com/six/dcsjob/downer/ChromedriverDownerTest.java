package com.six.dcsjob.downer;


import org.apache.commons.lang3.StringUtils;

import com.six.dcscrawler.downloader.AbstractDownloader;
import com.six.dcscrawler.downloader.DownloaderFactory;
import com.six.dcscrawler.downloader.DownloaderType;
import com.six.dcscrawler.modle.Page;

/**
 * @author liusong
 * @date 2017年8月30日
 * @email 359852326@qq.com
 */
public class ChromedriverDownerTest {

	static {
		String os = System.getProperties().getProperty("os.name").toUpperCase();
		String chromeDir = "/Users/liusong/git/dcs-crawler/webdriver/chrome/";
		String driverFilePath = null;
		if (os.indexOf("WIN") != -1) {
			driverFilePath = chromeDir + "chromedriver.exe";
		} else if (os.indexOf("MAC") != -1) {
			driverFilePath = chromeDir + "chromedriver_mac";
		} else {
			driverFilePath = chromeDir + "chromedriver";
		}
		System.setProperty("webdriver.chrome.driver", driverFilePath);
	}

	public static void main(String[] args) {
		AbstractDownloader downer = DownloaderFactory.getInstance().newDowner(null, DownloaderType.CHROME, 1000, null,
				null, false, true);
		String loginUrl = "http://www.qixin.com/auth/login?return_url=%2F";
		String userName = "18566655245";
		String pwd = "37dzsk9n5w";
		String userNameInputXpath = "input[placeholder='请输入手机号码']";
		String pwdInputXpath = "input[placeholder='请输入密码']";
		String loginBtXpath = "a[class='btn btn-primary btn-block btn-lg']";
		Page page = new Page();
		page.setUrl(loginUrl);
		try {
			downer.download(page);
			downer.setValue(userNameInputXpath, userName);
			downer.setValue(pwdInputXpath, pwd);
			downer.click(loginBtXpath);
			String wordFlag = "{word}";
			String urlTemplate = "http://www.qixin.com/search?key=" + wordFlag + "&page=1";
			String searchCompany = "腾讯";
			String newUrl = StringUtils.replace(urlTemplate, wordFlag, searchCompany);
			page.setUrl(newUrl);
			downer.download(page);
			System.out.println(page.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != downer) {
				downer.close();
			}
		}
	}

}
