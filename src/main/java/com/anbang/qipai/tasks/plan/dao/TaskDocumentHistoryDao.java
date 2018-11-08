package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;

public interface TaskDocumentHistoryDao {

	long getAmountByState(String state);

	List<TaskDocumentHistory> findTaskByState(String state);

	TaskDocumentHistory findTaskById(String taskId);

	void addTaskDocumentHistory(TaskDocumentHistory taskDocumentHistory);

	void updateState(String taskId, String state);
}
