package com.anbang.qipai.tasks.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.anbang.qipai.tasks.plan.dao.TaskDocumentHistoryDao;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;
import com.highto.framework.web.page.ListPage;

public class TaskDocumentHistoryService {
	@Autowired
	private TaskDocumentHistoryDao taskDocumentHistoryDao;

	public ListPage findTaskDocuments(int page, int size, TaskDocumentHistory taskDoc) {
		long amount = taskDocumentHistoryDao.getAmount(taskDoc);
		List<TaskDocumentHistory> taskDocList = taskDocumentHistoryDao.findTaskDocuments(page, size, taskDoc);
		ListPage listPage = new ListPage(taskDocList, page, size, (int) amount);
		return listPage;
	}

	public void updateState(String taskId, String state) {
		taskDocumentHistoryDao.updateState(taskId, state);
	}
}
