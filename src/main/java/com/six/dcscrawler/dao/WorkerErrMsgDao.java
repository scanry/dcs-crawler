package com.six.dcscrawler.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.six.dcscrawler.dao.provider.WorkerErrMsgDaoProvider;
import com.six.dcsjob.WorkerErrMsg;


/** 
* @author  作者 
* @E-mail: 359852326@qq.com 
* @date 创建时间：2017年2月16日 下午3:17:08 
*/
public interface WorkerErrMsgDao extends BaseDao{

	String QUERY_PARAM_JOBSNAPSHOTID="jobSnapshotId";
	String QUERY_PARAM_JOBNAME="jobName";
	String QUERY_PARAM_WORKERNAME="workerName";
	
	
	/**
	 * 通过任务名称，任务执行批次分页查询异常信息
	 * @param jobName           任务名称
	 * @param jobSnapshotId     任务执行批次
	 * @param start             分页起始位置
	 * @param end               分页偏移位置
	 * @return 匹配到的数据集合
	 */
	@SelectProvider(type = WorkerErrMsgDaoProvider.class, method = "pageQuery")
	public List<WorkerErrMsg> pageQuery(@Param("jobName")String jobName, 
			@Param("jobSnapshotId")String jobSnapshotId,
			@Param("start")int start, 
			@Param("end")int end);

	/**
	 * 通过任务名称，任务执行批次查询异常信息
	 * @param jobName       任务名称
	 * @param jobSnapshotId 任务执行批次
	 * @return 匹配到的数据集合
	 */
	@SelectProvider(type = WorkerErrMsgDaoProvider.class, method = "queryByJob")
	public List<WorkerErrMsg> queryByJob(@Param(QUERY_PARAM_JOBNAME)String jobName,
			@Param(QUERY_PARAM_JOBSNAPSHOTID)String jobSnapshotId);
	
	/**
	 * 保存worker运行异常信息
	 * @param workerErrMsg worker运行异常信息
	 * @return 受影响行数
	 */
	@InsertProvider(type = WorkerErrMsgDaoProvider.class, method = "save")
	public int save(WorkerErrMsg workerErrMsg);
	
	/**
	 * 批量保存worker运行异常信息
	 * @param list worker运行异常信息集合
	 * @return 受影响行数
	 */
	@InsertProvider(type = WorkerErrMsgDaoProvider.class, method = "batchSave")
	public int batchSave(@Param(BATCH_SAVE_PARAM)List<WorkerErrMsg> list);
	
	/**
	 * 删除多少天以前的数据
	 * @param beforeDays
	 * @return
	 */
	@Delete("delete from " + TableNames.JOB_WORKER_SNAPSHOT_ERRMSG_TABLE_NAME + " where datediff(curdate(),startTime)>=#{beforeDays}")
	public int delBeforeDate(int beforeDays);
}
