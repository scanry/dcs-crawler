package com.six.dcsjob.downer;

import org.apache.commons.lang3.StringUtils;

import com.six.dcscrawler.downer.AbstractDowner;
import com.six.dcscrawler.downer.DownerManager;
import com.six.dcscrawler.downer.DownerType;
import com.six.dcscrawler.modle.Page;

/**   
* @author liusong  
* @date   2017年8月17日 
* @email  359852326@qq.com 
*/
public class ChromeDownerTest {

	public static void main(String[] args) throws Exception {
		AbstractDowner downer=DownerManager.getInstance().newDowner(null,
				1000, DownerType.CHROME, null, null);
		String loginUrl="http://www.qixin.com/auth/login?return_url=%2F";
		String userName="18566655245";
		String pwd="37dzsk9n5w";
		String userNameInputXpath="//input[@placeholder='请输入手机号码']";
		String pwdInputXpath="//input[@placeholder='请输入密码']";
		String loginBtXpath="//a[contains(text(),'登录')]";
		Page page=new Page();
		page.setUrl(loginUrl);
		downer.download(page);
		downer.findWebElement(userNameInputXpath).sendKeys(userName);
		downer.findWebElement(pwdInputXpath).sendKeys(pwd);
		downer.findWebElement(loginBtXpath).click();
		String wordFlag="{word}";
		String urlTemplate="http://www.qixin.com/search?key="+wordFlag+"&page=1";
		String searchCompany="0884543英属哥伦比亚有限责任公司";
		String newUrl=StringUtils.replace(urlTemplate, wordFlag, searchCompany);
		page.setUrl(newUrl);
		downer.download(page);
		downer.close();
	}

}
