package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.domain.FinishTask;

public interface FinishTaskDao {

	List<FinishTask> findAllFinishTaskByMemberId(String memberId);
	
	List<FinishTask> findAllFinishTask();
	
	void addFinishTask(FinishTask finishTask);
}
