package com.six.dcscrawler.modle;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年2月13日 下午4:43:41
 */
public class HttpProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3569393980465582761L;
	private String host;// 代理主机ip
	private int port;// 代理主机端口
	private int type;//1.自己独立ip代理   2.阿布代理
	private String userName;// 代理账户
	private String passWord;// 代理密码
	private String expire;// 有效期20170310120000
	private String describe;// 代理密码
	private long lastUseTime;//上一次试用时间

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public long getLastUseTime() {
		return lastUseTime;
	}

	public void setLastUseTime(long lastUseTime) {
		this.lastUseTime = lastUseTime;
	}
	
	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

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
