package com.anbang.qipai.tasks.msg.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.tasks.msg.channel.source.MemberScoresSource;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;

@EnableBinding(MemberScoresSource.class)
public class MemberScoresMsgService {
	@Autowired
	private MemberScoresSource memberScoresSource;

	public void withdraw(String memberId, int amount, String textSummary) {
		CommonMO mo = new CommonMO();
		mo.setMsg("withdraw");
		Map data = new HashMap();
		data.put("memberId", memberId);
		data.put("amount", amount);
		data.put("textSummary", textSummary);
		mo.setData(data);
		memberScoresSource.memberScoresAccounting().send(MessageBuilder.withPayload(mo).build());
	}

	public void giveScoreToMember(String memberId, int amount, String textSummary) {
		CommonMO mo = new CommonMO();
		mo.setMsg("givescoretomember");
		Map data = new HashMap();
		data.put("memberId", memberId);
		data.put("amount", amount);
		data.put("textSummary", textSummary);
		mo.setData(data);
		memberScoresSource.memberScoresAccounting().send(MessageBuilder.withPayload(mo).build());
	}
}
