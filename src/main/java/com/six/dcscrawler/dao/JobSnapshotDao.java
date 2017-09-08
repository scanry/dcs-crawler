package com.six.dcscrawler.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.six.dcscrawler.dao.provider.JobSnapshotDaoProvider;
import com.six.dcsjob.model.JobSnapshot;


/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2016年10月24日 下午3:36:26
 */
public interface JobSnapshotDao extends BaseDao {

	/**
	 * 通过 jobSnapshotId 和 jobName 查询 任务运行记录
	 * 
	 * @param jobSnapshotId 任务运行批次
	 * @param jobName       任务名称
	 * @return 查询到的任务运行记录
	 */
	@SelectProvider(type = JobSnapshotDaoProvider.class, method = "query")
	public JobSnapshot query(@Param("id") String jobSnapshotId, @Param("name") String jobName);

	/**
	 * 通过任务名称查询 任务运行记录 的集合
	 * 
	 * @param jobName 任务名称
	 * @return 查询到的任务的运行记录 集合
	 */
	@SelectProvider(type = JobSnapshotDaoProvider.class, method = "queryByJob")
	public List<JobSnapshot> queryByJob(String jobName);

	/**
	 * 通过任务名称分页查询任务运行记录集合
	 * 
	 * @param jobName   任务名称
	 * @param pageIndex 分页查询起始位置
	 * @param pageSize  分页查询偏移位置
	 * @return 查询到的任务的运行记录 集合
	 */
	@SelectProvider(type = JobSnapshotDaoProvider.class, method = "pageQuery")
	public List<JobSnapshot> pageQuery(@Param("jobName") String jobName, @Param("start") int pageIndex,
			@Param("end") int pageSize);

	/**
	 * 通过任务名称查询 任务的最后一条运行记录
	 * 
	 * @param jobName 任务名称
	 * @return 最后一条运行记录
	 */
	@SelectProvider(type = JobSnapshotDaoProvider.class, method = "queryLastEnd")
	public JobSnapshot queryLastEnd(@Param("jobName") String jobName, @Param("excludeId") String excludeId);

	/**
	 * 保存任务运行记录
	 * 
	 * @param jobSnapshot
	 * @return 受影响数据的条数
	 */
	@InsertProvider(type = JobSnapshotDaoProvider.class, method = "save")
	public int save(JobSnapshot jobSnapshot);

	/**
	 * 更新任务运行记录
	 * 
	 * @param jobSnapshot
	 * @return 受影响数据的条数
	 */
	@InsertProvider(type = JobSnapshotDaoProvider.class, method = "update")
	public int update(JobSnapshot jobSnapshot);

	/**
	 * 更新任务运行记录的状态
	 * 
	 * @param id
	 * @param status
	 * @return 受影响数据的条数
	 */
	@InsertProvider(type = JobSnapshotDaoProvider.class, method = "updateStatus")
	public int updateStatus(@Param("version") int version, @Param("newVersion") int newVersion, @Param("id") String id,
			@Param("status") int status);

	/**
	 * 删除多少天以前的数据
	 * 
	 * @param beforeDays
	 * @return 受影响数据的条数
	 */
	@Delete("delete from " + TableNames.JOB_SNAPSHOT_TABLE_NAME + " where datediff(curdate(),startTime)>=#{beforeDays}")
	public int delBeforeDate(int beforeDays);

}
