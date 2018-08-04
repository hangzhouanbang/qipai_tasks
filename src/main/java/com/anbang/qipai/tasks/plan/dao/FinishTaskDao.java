package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.bean.FinishTask;

public interface FinishTaskDao {

	List<FinishTask> findFinishTaskByMemberId(String memberId);

	void addFinishTask(FinishTask finishTask);
}
