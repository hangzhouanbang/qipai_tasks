package com.anbang.qipai.tasks.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.plan.dao.TaskDocumentHistoryDao;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

@Service
public class TaskDocumentHistoryService {
	@Autowired
	private TaskDocumentHistoryDao taskDocumentHistoryDao;

	public TaskDocumentHistory findTaskById(String taskId) {
		return taskDocumentHistoryDao.findTaskById(taskId);
	}

	public void releaseTask(TaskDocumentHistory task) {
		task.setReleaseTime(System.currentTimeMillis());
		task.setState(1);
		taskDocumentHistoryDao.addTaskDocumentHistory(task);
	}

	public boolean withdrawTaskDocumentHistory(String[] taskIds) {
		return taskDocumentHistoryDao.updateState(taskIds, 0);
	}
}
