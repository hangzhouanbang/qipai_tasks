package com.anbang.qipai.tasks.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.dao.TaskDocumentHistoryDao;

@Service
public class TaskDocumentHistoryService {
	@Autowired
	private TaskDocumentHistoryDao taskDocumentHistoryDao;

	public void releaseTask(TaskDocumentHistory task) {
		taskDocumentHistoryDao.addTaskDocumentHistory(task);
	}

	public boolean withdrawTaskDocumentHistory(String[] taskIds) {
		return taskDocumentHistoryDao.updateState(taskIds, 0);
	}
}
