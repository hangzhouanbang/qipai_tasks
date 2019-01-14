package com.anbang.qipai.tasks.msg.channel.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface FinishTasksSource {
	@Output
	MessageChannel finishTasks();
}
