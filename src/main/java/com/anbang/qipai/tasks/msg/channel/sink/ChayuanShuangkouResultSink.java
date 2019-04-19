package com.anbang.qipai.tasks.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ChayuanShuangkouResultSink {
	String GAMECHAYUANSKRESULT = "gameChayuanskResult";

	@Input
	SubscribableChannel gameChayuanskResult();
}
