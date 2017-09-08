package com.six.dcscrawler.modle;

import java.util.Map;

import org.jsoup.nodes.Document;

import com.six.dcscrawler.downloader.ContentType;
import com.six.dcsjob.model.Index;
import com.six.dcsjob.space.WorkSpaceData;

import lombok.Data;
import okhttp3.Headers;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
@Data
public class Page implements WorkSpaceData{
	
	private String url;
	private String referer;
	private String finalUrl;
	private int needDownload=1;
	private String title;
	private String content;
	private byte[] contentBytes;
	private HttpMethod httpMethod;
	private PostType postType;
	private ContentType contentType;
	private Map<String,Object> postData;
	private Map<String,String> head;
	private transient IpProxy ipProxy;
	private transient int httpCode;
	private transient Headers headers;
	private String charset;
	private transient String redirectedUrl;
	private transient Document document;
	private int retryCount;

	@Override
	public void setIndex(Index index) {
		
	}

	@Override
	public Index getIndex() {
		return null;
	}

	@Override
	public String getKey() {
		return null;
	}

}
