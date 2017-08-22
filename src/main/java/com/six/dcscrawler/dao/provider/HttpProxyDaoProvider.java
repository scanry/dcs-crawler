package com.six.dcscrawler.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import com.six.dcscrawler.dao.TableNames;
import com.six.dcscrawler.modle.IpProxy;


/** 
* @author  作者 
* @E-mail: 359852326@qq.com 
* @date 创建时间：2017年5月19日 上午9:31:25 
*/
public class HttpProxyDaoProvider {

	public String save(IpProxy httpProxy) {
		String columns = "`host`,"
				+ "`port`,"
				+ "`type`,"
				+ "`userName`,"
				+ "`passWord`,"
				+ "`expire`,"
				+ "`describe`";
		
		String values = "#{host},"
				+ "#{port},"
				+ "#{type},"
				+ "#{userName},"
				+ "#{passWord},"
				+ "#{expire},"
				+ "#{describe}";
		SQL sql = new SQL();
		sql.INSERT_INTO(TableNames.HTTP_PROXY_TABLE_NAME);
		sql.VALUES(columns, values);
		return sql.toString();
	}
}
