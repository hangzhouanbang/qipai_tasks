package com.anbang.qipai.tasks.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.tasks.msg.channel.source.FinishTasksSource;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.bean.FinishedTask;

@EnableBinding(FinishTasksSource.class)
public class FinishTasksMsgService {
	@Autowired
	private FinishTasksSource finishTasksSource;

	public void finishTask(FinishedTask finishTask) {
		CommonMO mo = new CommonMO();
		mo.setMsg("finish task");
		mo.setData(finishTask);
		finishTasksSource.finishTasks().send(MessageBuilder.withPayload(mo).build());
	}
}
