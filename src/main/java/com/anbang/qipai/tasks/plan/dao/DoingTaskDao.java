package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.domain.DoingTask;

public interface DoingTaskDao {

	List<DoingTask> findAllDoingTaskByMemberId(String memberId);

	List<DoingTask> findAllDoingTask();

	void addDoingTask(DoingTask doingTask);

	List<DoingTask> findDoingTaskByMemberIdAndType(String memberId, String type);

	DoingTask findDoingTaskById(String doingTaskId);
}
