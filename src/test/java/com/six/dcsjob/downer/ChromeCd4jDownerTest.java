package com.six.dcsjob.downer;

import org.apache.commons.lang3.StringUtils;

import com.six.dcscrawler.downloader.AbstractDownloader;
import com.six.dcscrawler.downloader.DownloaderFactory;
import com.six.dcscrawler.downloader.DownloaderType;
import com.six.dcscrawler.modle.Page;

/**   
* @author liusong  
* @date   2017年8月17日 
* @email  359852326@qq.com 
*/
public class ChromeCd4jDownerTest {

	public static void main(String[] args) throws Exception {
		AbstractDownloader downer=DownloaderFactory.getInstance().newDowner(null, DownloaderType.HEADLESS_CHROME,
				1000, null, null,true,true);
		String loginUrl="http://www.qixin.com/auth/login?return_url=%2F";
		String userName="18566655245";
		String pwd="37dzsk9n5w";
		String userNameInputXpath="input[placeholder='请输入手机号码']";
		String pwdInputXpath="input[placeholder='请输入密码']";
		String loginBtXpath="a[class='btn btn-primary btn-block btn-lg']";
		Page page=new Page();
		page.setUrl(loginUrl);
		try{
			downer.download(page);
			downer.setValue(userNameInputXpath,userName);
			downer.setValue(pwdInputXpath,pwd);
			downer.click(loginBtXpath);
			String wordFlag="{word}";
			String urlTemplate="http://www.qixin.com/search?key="+wordFlag+"&page=1";
			String searchCompany="0884543英属哥伦比亚有限责任公司";
			String newUrl=StringUtils.replace(urlTemplate, wordFlag, searchCompany);
			page.setUrl(newUrl);
			downer.download(page);
			System.out.println(page.getContent());
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(null!=downer){
				downer.close();
			}
		}
	}

}
