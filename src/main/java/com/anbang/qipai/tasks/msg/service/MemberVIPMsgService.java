package com.anbang.qipai.tasks.msg.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.tasks.msg.channel.source.MemberVIPSource;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;

@EnableBinding(MemberVIPSource.class)
public class MemberVIPMsgService {
	@Autowired
	private MemberVIPSource memberVIPSource;

	public void rewardVip(String memberId, long vipTime, String summary) {
		CommonMO mo = new CommonMO();
		mo.setMsg("rewardVip");
		Map data = new HashMap();
		data.put("memberId", memberId);
		data.put("vipTime", vipTime);
		data.put("summary", summary);
		mo.setData(data);
		memberVIPSource.memberVIPAccounting().send(MessageBuilder.withPayload(mo).build());
	}
}
