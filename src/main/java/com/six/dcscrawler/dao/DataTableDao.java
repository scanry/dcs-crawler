package com.six.dcscrawler.dao;

import org.apache.ibatis.annotations.UpdateProvider;

import com.six.dcscrawler.dao.provider.DataTableDaoProvider;


/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年2月15日 下午6:19:46
 */
public interface DataTableDao extends BaseDao{

	@UpdateProvider(type = DataTableDaoProvider.class, method = "createTable")
	public int createTable(String creatTableSql);
}
