package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

public interface TaskDocumentHistoryDao {
	long getAmount(TaskDocumentHistory taskDoc);

	List<TaskDocumentHistory> findTaskDocuments(int page, int size, TaskDocumentHistory taskDoc);

	void add(TaskDocumentHistory taskDocumentHistory);

	void updateState(String taskId, String state);
}
