package com.six.dcscrawler.modle;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**   
* @author liusong  
* @date   2017年8月15日 
* @email  359852326@qq.com 
*/
@Data
public class IpProxy implements Serializable{

	private static final long serialVersionUID = -3569393980465582761L;
	private String host;// 代理主机ip
	private int port;// 代理主机端口
	private int type;//1.自己独立ip代理   2.阿布代理
	private String userName;// 代理账户
	private String passWord;// 代理密码
	private String expire;// 有效期20170310120000
	private String describe;// 代理密码
	private long lastUseTime;//上一次试用时间

	public int hashCode() {
		return toString().hashCode();
	}

	public boolean equals(Object object) {
		HttpProxy target = (HttpProxy) object;
		return host.equals(target.getHost()) && port == target.getPort();
	}
	
	public String toStringUserAndpwd() {
		return userName+":"+passWord+"@"+host + ":" + port;
	}

	public String toString() {
		return host + ":" + port;
	}
	
	/**
	 * haizhi:haizhi@119.186.237.209:8888
	 * 根据给的代理字符串构造一个代理对象
	 * @param httpProxyStr
	 * @param describe
	 * @return
	 */
	public static HttpProxy build(String httpProxyStr,String describe){
		String[] temp=StringUtils.split(httpProxyStr,"@");
		String[] userNameAndPwd=StringUtils.split(temp[0],":");
		String[] hostAndPort=StringUtils.split(temp[1],":");
		HttpProxy httpProxy=new HttpProxy();
		httpProxy.setHost(hostAndPort[0]);
		httpProxy.setPort(Integer.valueOf(hostAndPort[1]));
		httpProxy.setUserName(userNameAndPwd[0]);
		httpProxy.setPassWord(userNameAndPwd[1]);
		httpProxy.setDescribe(describe);
		return httpProxy;
	}

}
