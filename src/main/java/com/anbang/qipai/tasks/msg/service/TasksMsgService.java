package com.anbang.qipai.tasks.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.tasks.msg.channel.TasksSoure;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;

@EnableBinding(TasksSoure.class)
public class TasksMsgService {

	@Autowired
	private TasksSoure taksSoure;

	public void releaseTask(TaskDocumentHistory task) {
		CommonMO mo = new CommonMO();
		mo.setMsg("release task");
		mo.setData(task);
		taksSoure.tasks().send(MessageBuilder.withPayload(mo).build());
	}

	public void withdrawTaskDocumentHistory(TaskDocumentHistory task) {
		CommonMO mo = new CommonMO();
		mo.setMsg("withdraw task");
		mo.setData(task);
		taksSoure.tasks().send(MessageBuilder.withPayload(mo).build());
	}
}