package com.anbang.qipai.tasks.msg.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MemberRewardSoure {

	@Output
	MessageChannel taskreward();
}
