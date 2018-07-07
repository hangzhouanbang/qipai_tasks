package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.domain.Task;

public interface TaskDao {

	List<Task> findTaskByMemberId(String memberId);

	List<Task> findTasksByType(int page, int size, String type);

	long getAmountByType(String type);

	void addTask(Task task);

	List<Task> findTaskByMemberIdAndType(String memberId, String type);

	Task findTaskById(String taskId);

	boolean updateTask(Task task);

	boolean deleteTaskById(String taskId);
}
