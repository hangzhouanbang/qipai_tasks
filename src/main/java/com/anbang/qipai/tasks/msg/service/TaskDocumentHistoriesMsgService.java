package com.anbang.qipai.tasks.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.tasks.msg.channel.TaskDocumentHistoriesSource;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

@EnableBinding(TaskDocumentHistoriesSource.class)
public class TaskDocumentHistoriesMsgService {
	@Autowired
	private TaskDocumentHistoriesSource taskDocumentHistoriesSource;

	public void addTaskDocumentHistory(TaskDocumentHistory taskDocumentHistory) {
		CommonMO mo = new CommonMO();
		mo.setMsg("addTaskDocumentHistory");
		mo.setData(taskDocumentHistory);
		taskDocumentHistoriesSource.taskdocumenthistories().send(MessageBuilder.withPayload(mo).build());
	}
}
