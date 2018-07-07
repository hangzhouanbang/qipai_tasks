package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

public interface TaskDocumentHistoryDao {

	long getAmountByReleaseTime(long releaseTime);

	List<TaskDocumentHistory> findTaskByReleaseTime(int page, int size, long releaseTime);

	void addTaskDocumentHistory(TaskDocumentHistory taskDocumentHistory);

	boolean updateState(String[] taskIds, int state);
}
