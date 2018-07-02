package com.anbang.qipai.tasks.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.tasks.msg.channel.TaskDocumentsSource;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.domain.TaskDocument;

@EnableBinding(TaskDocumentsSource.class)
public class TaskDocumentsMsgService {
	@Autowired
	private TaskDocumentsSource taskDocumentsSource;

	public void addTaskDocument(TaskDocument taskDoc) {
		CommonMO mo = new CommonMO();
		mo.setMsg("addTaskDocument");
		mo.setData(taskDoc);
		taskDocumentsSource.taskdocuments().send(MessageBuilder.withPayload(mo).build());
	}

	public void updateTaskDocument(TaskDocument taskDoc) {
		CommonMO mo = new CommonMO();
		mo.setMsg("updateTaskDocument");
		mo.setData(taskDoc);
		taskDocumentsSource.taskdocuments().send(MessageBuilder.withPayload(mo).build());
	}

	public void deleteTaskDocuments(String[] taskIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg("deleteTaskDocuments");
		mo.setData(taskIds);
		taskDocumentsSource.taskdocuments().send(MessageBuilder.withPayload(mo).build());
	}

}
