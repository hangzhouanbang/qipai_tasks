package com.anbang.qipai.tasks.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MemberInvitationRecordSink {
	String MEMBERINVITATIONRECORD = "memberInvitationRecord";

	@Input
	SubscribableChannel memberInvitationRecord();
}
