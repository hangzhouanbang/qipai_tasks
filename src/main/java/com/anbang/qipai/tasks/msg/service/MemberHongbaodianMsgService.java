package com.anbang.qipai.tasks.msg.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.tasks.msg.channel.source.MemberHongbaodianSource;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;

@EnableBinding(MemberHongbaodianSource.class)
public class MemberHongbaodianMsgService {
	@Autowired
	private MemberHongbaodianSource memberHongbaodianSource;

	public void withdraw(String memberId, int amount, String textSummary) {
		CommonMO mo = new CommonMO();
		mo.setMsg("withdraw");
		Map data = new HashMap();
		data.put("memberId", memberId);
		data.put("amount", amount);
		data.put("textSummary", textSummary);
		mo.setData(data);
		memberHongbaodianSource.memberHongbaodianAccounting().send(MessageBuilder.withPayload(mo).build());
	}

	public void giveHongbaodianToMember(String memberId, int amount, String textSummary) {
		CommonMO mo = new CommonMO();
		mo.setMsg("give_hongbaodian_to_member");
		Map data = new HashMap();
		data.put("memberId", memberId);
		data.put("amount", amount);
		data.put("textSummary", textSummary);
		mo.setData(data);
		memberHongbaodianSource.memberHongbaodianAccounting().send(MessageBuilder.withPayload(mo).build());
	}
}
