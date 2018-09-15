package com.anbang.qipai.tasks.msg.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface WenzhouMajiangResultSink {
	String WENZHOUMAJIANGRESULT = "wenzhouMajiangResult";

	@Input
	SubscribableChannel wenzhouMajiangResult();
}
