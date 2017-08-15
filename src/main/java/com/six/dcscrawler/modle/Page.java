package com.six.dcscrawler.modle;

import java.util.Map;

import com.six.dcsjob.Index;
import com.six.dcsjob.WorkSpaceData;

import lombok.Data;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
@Data
public class Page implements WorkSpaceData{
	
	private String url;
	private String referer;
	private String finalRerer;
	private int needDownload;
	private String content;
	private byte[] contentBytes;
	private HttpMethod httpMethod;
	private PostType postType;
	private Map<String,Object> postData;
	private transient IpProxy ipProxy;

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
