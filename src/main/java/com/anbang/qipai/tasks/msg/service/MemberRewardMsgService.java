package com.anbang.qipai.tasks.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.anbang.qipai.tasks.msg.channel.MemberRewardSoure;

@EnableBinding(MemberRewardSoure.class)
public class MemberRewardMsgService {
	
	@Autowired
	private MemberRewardSoure memberRewardSoure;
	

}
