package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistoryState;

public interface TaskDocumentHistoryDao {

	long getAmountByState(TaskDocumentHistoryState state);

	List<TaskDocumentHistory> findTaskByState(TaskDocumentHistoryState state);

	TaskDocumentHistory findTaskById(String taskId);

	void addTaskDocumentHistory(TaskDocumentHistory taskDocumentHistory);

	void updateState(String[] taskIds, TaskDocumentHistoryState state);
}
