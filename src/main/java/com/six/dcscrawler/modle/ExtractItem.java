package com.six.dcscrawler.modle;

import lombok.Data;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/

@Data
public class ExtractItem {

	private String jobName;// job名字

	private int serialNub;// 抽取顺序

	private String pathName;// 名字

	private int primary;// 是否是主键 0:不是 1是

	private ExtractItemType type;// 类型

	/**
	 * 0：不输出
	 * 1:输出至保存 
	 * 2:输出到生成新page meta中 
	 * 3:url类型生成新page并输出到对了中
	 */
	private int outputType;

	private String outputKey;// 结果 key
	
	private String httpMethod;//httpMethod

	private int mustHaveResult;// 是否必须有值 1 ：必须有值

	private String describe;// 描述
}
