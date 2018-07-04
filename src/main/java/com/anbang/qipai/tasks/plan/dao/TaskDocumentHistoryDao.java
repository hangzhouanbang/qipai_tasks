package com.anbang.qipai.tasks.plan.dao;

import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

public interface TaskDocumentHistoryDao {

	TaskDocumentHistory findTaskById(String taskId);

	void addTaskDocumentHistory(TaskDocumentHistory taskDocumentHistory);

	boolean updateState(String taskId, int state);
}
