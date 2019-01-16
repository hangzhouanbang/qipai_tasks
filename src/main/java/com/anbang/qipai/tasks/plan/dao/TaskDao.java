package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskType;

public interface TaskDao {

	List<Task> findTaskByMemberId(String memberId);

	List<Task> findTasksByType(int page, int size, TaskType taskType);

	long getAmountByType(TaskType taskType);

	void addTask(Task task);

	List<Task> findTaskByMemberIdAndType(String memberId, String type);

	List<Task> findTaskByMemberIdAndTaskName(String memberId, String taskName);

	Task findTaskById(String taskId);

	/**
	 * 根据任务历史id和玩家id查询任务
	 */
	Task findTaskByMemberIdAndTaskId(String memberId, String taskId);

	void updateTask(Task task);

	void deleteTaskById(String taskId);
}
