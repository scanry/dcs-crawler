package com.six.dcscrawler.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.six.dcscrawler.dao.provider.WorkerSnapshotDaoProvider;
import com.six.dcsjob.model.WorkerSnapshot;


/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年2月16日 下午1:33:32 worker 运行记录(WorkerSnapshotDao)访问接口
 */
public interface WorkerSnapshotDao extends BaseDao {

	/**
	 * 通过任务名称和任务运行批次(jobSnapshotId)查询
	 * 
	 * @param jobName 任务名称
	 * @param jobSnapshotId 任务运行批次
	 * @return 匹配到的数据集合
	 */
	@SelectProvider(type = WorkerSnapshotDaoProvider.class, method = "query")
	public List<WorkerSnapshot> query(@Param("jobName") String jobName, @Param("jobSnapshotId") String jobSnapshotId);

	/**
	 * 保存 worker 运行记录
	 * @param workerSnapshot worker 运行记录
	 * @return 受影响行数
	 */
	@InsertProvider(type = WorkerSnapshotDaoProvider.class, method = "save")
	public int save(WorkerSnapshot workerSnapshot);
	
	/**
	 * 保存 worker 运行记录集合
	 * @param workerSnapshots
	 */
	@InsertProvider(type = WorkerSnapshotDaoProvider.class, method = "batchSave")
	public void batchSave(@Param(BATCH_SAVE_PARAM) List<WorkerSnapshot> workerSnapshots);

	/**
	 * 更新 worker 运行记录
	 * @param workerSnapshot worker 运行记录
	 * @return 受影响行数
	 */
	@InsertProvider(type = WorkerSnapshotDaoProvider.class, method = "update")
	public int update(WorkerSnapshot workerSnapshot);

	/**
	 * 删除多少天以前的数据
	 * 
	 * @param beforeDays
	 * @return 受影响的行数
	 */
	@Delete("delete from " + TableNames.JOB_WORKER_SNAPSHOT_TABLE_NAME
			+ " where datediff(curdate(),startTime)>=#{beforeDays}")
	public int delBeforeDate(int beforeDays);

}
