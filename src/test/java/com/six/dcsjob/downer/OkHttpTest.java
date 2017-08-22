package com.six.dcsjob.downer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.six.dcscrawler.downer.AbstractDowner;
import com.six.dcscrawler.downer.DownerManager;
import com.six.dcscrawler.downer.DownerType;
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
		String url="http://openlaw.cn/search/judgement/default?type=&typeValue=&courtId=&lawFirmId=&lawyerId=&docType=&causeId=&judgeDateYear=&lawSearch=&litigationType=&judgeId=&procedureType=&judgeResult=&courtLevel=&procedureType=&zone=&keyword=惠州市惠阳区农村信用合作联社&page=1";
		AbstractDowner downer=DownerManager.getInstance().newDowner(null,
				1000, DownerType.OKHTTP, null, null);
		Page page=new Page();
		page.setUrl(url);
		page.setHttpMethod(HttpMethod.GET);
		Map<String,String> head=new HashMap<>();
		head.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
		head.put("X-Requested-With", "XMLHttpRequest");
		page.setHead(head);
		downer.download(page);
		System.out.println(page.getContent());
	}
	
	public void testQixinbao(){
		AbstractDowner downer=DownerManager.getInstance().newDowner(null,
				1000, DownerType.OKHTTP, null, null);
		String url="http://www.qixin.com/api/search";
		Page page=new Page();
		page.setUrl(url);
		page.setHttpMethod(HttpMethod.POST);
		Map<String,Object> params=new HashMap<>();
		params.put("key","地产");
		params.put("page",2);
		page.setPostData(params);
		page.setPostType(PostType.JSON);
		page.setReferer("http://www.qixin.com/search?key=%E5%9C%B0%E4%BA%A7&page=2");
		Map<String,String> head=new HashMap<>();
		head.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
		head.put("X-Requested-With", "XMLHttpRequest");
		head.put("Cookie","aliyungf_tc=AQAAAOKgEzB53QwAUMeLdx0w7IZNU3E+; channel=baidu; cookieShowLoginTip=1; responseTimeline=42; Hm_lvt_52d64b8d3f6d42a2e416d59635df3f71=1502935660; Hm_lpvt_52d64b8d3f6d42a2e416d59635df3f71=1502935688; _zg=%7B%22uuid%22%3A%20%2215dedf234b126d-0ca16c03e7fa3b-143a6d54-fa000-15dedf234b29e9%22%2C%22sid%22%3A%201502935659.699%2C%22updated%22%3A%201502935688.56%2C%22info%22%3A%201502935659702%7D");
		head.put("d3a4220192b71e2688bb", "55c83791ce90f8e1a9ac26a19cdfae976e3af31a3fd8c5da1c823df4f20489e21caadf98df109426355ee4d92598980ec803e4a4dd507e3effcfe73d026146a3");
		page.setHead(head);
		downer.download(page);
		System.out.println(page.getContent());
	}
}
