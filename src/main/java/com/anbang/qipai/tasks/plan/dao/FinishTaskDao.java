package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.bean.FinishedTask;

public interface FinishTaskDao {

	List<FinishedTask> findFinishTaskByMemberId(String memberId);

	FinishedTask findFinishTaskByMemberIdAndTaskId(String memberId, String taskId);

	void addFinishTask(FinishedTask finishTask);
}
