package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;

public interface TaskDocumentHistoryDao {

	long getAmountByReleaseTime(long releaseTime);

	List<TaskDocumentHistory> findTaskByReleaseTime(int page, int size, long releaseTime);

	TaskDocumentHistory findTaskById(String taskId);

	void addTaskDocumentHistory(TaskDocumentHistory taskDocumentHistory);

	boolean updateState(String[] taskIds, int state);
}
