package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.domain.TaskDocument;

public interface TaskDocumentDao {

	long getAmount(TaskDocument taskDoc);

	void addTaskDocument(TaskDocument taskDoc);

	void deleteTaskDocument(String[] taskIds);

	void updateTaskDocument(TaskDocument taskDoc);

	List<TaskDocument> findTaskDocuments(int page, int size, TaskDocument taskDoc);

	TaskDocument findDocumentById(String documentId);
}
