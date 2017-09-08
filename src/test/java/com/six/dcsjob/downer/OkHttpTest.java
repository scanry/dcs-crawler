package com.six.dcsjob.downer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.six.dcscrawler.downloader.AbstractDownloader;
import com.six.dcscrawler.downloader.DownloaderFactory;
import com.six.dcscrawler.downloader.DownloaderType;
import com.six.dcscrawler.modle.HttpMethod;
import com.six.dcscrawler.modle.Page;
import com.six.dcscrawler.modle.PostType;

/**   
* @author liusong  
* @date   2017年8月17日 
* @email  359852326@qq.com 
*/
public class OkHttpTest {

	public static void main(String[] args) throws IOException {
		//testJsoup();
		testQixinbaoLogin();
	}
	
	public static void testQixinbaoLogin(){
		AbstractDownloader downer=DownloaderFactory.getInstance().newDowner(null,DownloaderType.OKHTTP,
				1000,  null, null,false,false);
		String url="http://www.qixin.com/api/user/login";
		Page page=new Page();
		page.setUrl(url);
		page.setHttpMethod(HttpMethod.POST);
		Map<String,Object> params=new HashMap<>();
		params.put("acc","18566655245");
		params.put("pass","37dzsk9n5w");
		Map<String,Object> captcha=new HashMap<>();
		captcha.put("isTrusted", true);
		params.put("captcha", captcha);
		page.setPostData(params);
		page.setPostType(PostType.JSON);
		page.setReferer("hhttp://www.qixin.com/auth/login?return_url=%2Fsearch%3Fkey%3D%25E5%259C%25B0%25E4%25BA%25A7%26page%3D3");
		Map<String,String> head=new HashMap<>();
		head.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
		head.put("Origin", "http://www.qixin.com");
		head.put("X-Requested-With", "XMLHttpRequest");
		//head.put("Cookie","aliyungf_tc=AQAAAOKgEzB53QwAUMeLdx0w7IZNU3E+; channel=baidu; cookieShowLoginTip=1; responseTimeline=42; Hm_lvt_52d64b8d3f6d42a2e416d59635df3f71=1502935660; Hm_lpvt_52d64b8d3f6d42a2e416d59635df3f71=1502935688; _zg=%7B%22uuid%22%3A%20%2215dedf234b126d-0ca16c03e7fa3b-143a6d54-fa000-15dedf234b29e9%22%2C%22sid%22%3A%201502935659.699%2C%22updated%22%3A%201502935688.56%2C%22info%22%3A%201502935659702%7D");
		head.put("76eac628969e70eab74f", "24baa52f3e57e5c83c17ecc521a28c266fd3aa6aa264232fff0bc3006cd4fc86f27f163005be7921ad2df32432d5132bcbaa6316b6b382099cd5ea01bc3dbdf0");
		page.setHead(head);
		//downer.download(page);
		System.out.println("httpcode"+page.getHttpCode());
		System.out.println(page.getContent());
		String getUrl="http://www.qixin.com/search?key=地产&page=1";
		Page getPage=new Page();
		getPage.setUrl(getUrl);
		getPage.setHttpMethod(HttpMethod.GET);
		downer.download(getPage);
		System.out.println("httpcode"+getPage.getHttpCode());
		System.out.println(getPage.getContent());
	}
	
	public void testQixinbao(){
		AbstractDownloader downer=DownloaderFactory.getInstance().newDowner(null,DownloaderType.OKHTTP,
				1000,  null, null,false,false);
		String url="http://www.qixin.com/api/search";
		Page page=new Page();
		page.setUrl(url);
		page.setHttpMethod(HttpMethod.POST);
		Map<String,Object> params=new HashMap<>();
		params.put("key","地产");
		params.put("page",2);
		page.setPostData(params);
		page.setPostType(PostType.JSON);
		page.setReferer("hhttp://www.qixin.com/auth/login?return_url=%2Fsearch%3Fkey%3D%25E5%259C%25B0%25E4%25BA%25A7%26page%3D3");
		Map<String,String> head=new HashMap<>();
		head.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
		head.put("X-Requested-With", "XMLHttpRequest");
		head.put("Cookie","aliyungf_tc=AQAAANGw5RVCKgIAcsSLd0eUxFyeYnJv; channel=baidu; cookieShowLoginTip=1; responseTimeline=36; _zg=%7B%22uuid%22%3A%20%2215e0819ec0b727-0161d7bb9fb77e-31627c01-fa000-15e0819ec0c369%22%2C%22sid%22%3A%201503478145.724%2C%22updated%22%3A%201503478395.89%2C%22info%22%3A%201503374470163%2C%22cuid%22%3A%20%224e55ccca-273f-4f0c-8f8b-4bb9da9d02e8%22%7D; Hm_lvt_52d64b8d3f6d42a2e416d59635df3f71=1503374470,1503478148; Hm_lpvt_52d64b8d3f6d42a2e416d59635df3f71=1503478396");
		page.setHead(head);
		downer.download(page);
		System.out.println(page.getContent());
	}
}
