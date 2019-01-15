package com.anbang.qipai.tasks.msg.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.tasks.msg.channel.source.MemberHongbaoRMBSource;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;

@EnableBinding(MemberHongbaoRMBSource.class)
public class MemberHongbaoRMBMsgService {
	@Autowired
	private MemberHongbaoRMBSource memberHongbaoRMBSource;

	public void giveHongbaoRMBToMember(String memberId, double amount, String textSummary) {
		CommonMO mo = new CommonMO();
		mo.setMsg("give_hongbaormb_to_member");
		Map data = new HashMap();
		data.put("memberId", memberId);
		data.put("amount", amount);
		data.put("textSummary", textSummary);
		mo.setData(data);
		memberHongbaoRMBSource.memberHongbaoRMBAccounting().send(MessageBuilder.withPayload(mo).build());
	}
}
