package com.six.dcscrawler.dao.provider;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.six.dcscrawler.dao.BaseDao;
import com.six.dcscrawler.dao.TableNames;
import com.six.dcsjob.model.WorkerSnapshot;


/** 
* @author  作者 
* @E-mail: 359852326@qq.com 
* @date 创建时间：2017年2月16日 下午1:35:08 
*/
public class WorkerSnapshotDaoProvider extends BaseProvider{
	
	private String saveColumns="jobName,"
			+ "jobSnapshotId,"
			+ "`name`,"
			+ "localNode,"
			+ "`status`,"
			+ "startTime,"
			+ "endTime,"
			+ "totalProcessCount,"
			+ "totalResultCount,"
			+ "totalProcessTime,"
			+ "avgProcessTime,"
			+ "maxProcessTime,"
			+ "minProcessTime,"
			+ "errCount";
	public String query(Map<String, Object> map) {
		SQL sql=new SQL();
		String columns="jobName,"
				+ "jobSnapshotId,"
				+ "`name`,"
				+ "localNode,"
				+ "`status`,"
				+ "UNIX_TIMESTAMP(startTime)*1000 startTime,"
				+ "UNIX_TIMESTAMP(endTime)*1000 endTime,"
				+ "totalProcessCount,"
				+ "totalResultCount,"
				+ "totalProcessTime,"
				+ "avgProcessTime,"
				+ "maxProcessTime,"
				+ "minProcessTime,"
				+ "errCount";
		sql.SELECT(columns);
		sql.FROM(TableNames.JOB_WORKER_SNAPSHOT_TABLE_NAME);
		sql.WHERE("jobName=#{jobName} and jobSnapshotId=#{jobSnapshotId}");
		return sql.toString();
	}
	public String save(WorkerSnapshot workerSnapshot) {
		String values="#{jobName},"
				+ "#{jobSnapshotId},"
				+ "#{name},"
				+ "#{localNode},"
				+ "#{status},"
				+ "#{startTime},"
				+ "#{endTime},"
				+ "#{totalProcessCount},"
				+ "#{totalResultCount},"
				+ "#{totalProcessTime},"
				+ "#{avgProcessTime},"
				+ "#{maxProcessTime},"
				+ "#{minProcessTime},"
				+ "#{errCount}";
		SQL sql=new SQL();
		sql.INSERT_INTO(TableNames.JOB_WORKER_SNAPSHOT_TABLE_NAME);
		sql.VALUES(saveColumns, values);
		return sql.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String batchSave(Map<String, Object> map) {
		List<WorkerSnapshot> workerSnapshots = (List<WorkerSnapshot>) map.get(BaseDao.BATCH_SAVE_PARAM);
		String values="(#{list["+INDEX_FLAG+"].jobName},"
				+ "#{list["+INDEX_FLAG+"].jobSnapshotId},"
				+ "#{list["+INDEX_FLAG+"].name},"
				+ "#{list["+INDEX_FLAG+"].localNode},"
				+ "#{list["+INDEX_FLAG+"].status},"
				+ "#{list["+INDEX_FLAG+"].startTime},"
				+ "#{list["+INDEX_FLAG+"].endTime},"
				+ "#{list["+INDEX_FLAG+"].totalProcessCount},"
				+ "#{list["+INDEX_FLAG+"].totalResultCount},"
				+ "#{list["+INDEX_FLAG+"].totalProcessTime},"
				+ "#{list["+INDEX_FLAG+"].avgProcessTime},"
				+ "#{list["+INDEX_FLAG+"].maxProcessTime},"
				+ "#{list["+INDEX_FLAG+"].minProcessTime},"
				+ "#{list["+INDEX_FLAG+"].errCount})";
		StringBuilder sbd = new StringBuilder();  
		sbd.append("insert into ").append(TableNames.JOB_WORKER_SNAPSHOT_TABLE_NAME);  
		sbd.append("(").append(saveColumns).append(") ");  
		sbd.append("values");  
		sbd.append(setBatchSaveSql(values,workerSnapshots));
		return sbd.toString();
	}
	
	public String update(WorkerSnapshot workerSnapshot) {
		SQL sql=new SQL();
		sql.UPDATE(TableNames.JOB_WORKER_SNAPSHOT_TABLE_NAME);
		sql.SET("`status` = #{status}");
		sql.SET("`endTime` = #{endTime}");
		sql.SET("totalProcessCount = #{totalProcessCount}");
		sql.SET("totalResultCount = #{totalResultCount}");
		sql.SET("totalProcessTime = #{totalProcessTime}");
		sql.SET("avgProcessTime = #{avgProcessTime}");
		sql.SET("maxProcessTime = #{maxProcessTime}");
		sql.SET("minProcessTime = #{minProcessTime}");
		sql.SET("errCount = #{errCount}");
		sql.WHERE("`jobName` = #{jobName} and `jobSnapshotId` = #{jobSnapshotId} and `name` = #{name}");
		return sql.toString();
	}


}
