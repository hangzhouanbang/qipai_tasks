package com.anbang.qipai.tasks.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.config.TaskDocumentHistoryState;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.dao.TaskDocumentHistoryDao;

@Service
public class TaskDocumentHistoryService {

	@Autowired
	private TaskDocumentHistoryDao taskDocumentHistoryDao;

	public void releaseTask(TaskDocumentHistory task) {
		taskDocumentHistoryDao.addTaskDocumentHistory(task);
	}

	public TaskDocumentHistory withdrawTaskDocumentHistory(String taskId) {
		taskDocumentHistoryDao.updateState(taskId, TaskDocumentHistoryState.STOP);
		return taskDocumentHistoryDao.findTaskById(taskId);
	}
}
