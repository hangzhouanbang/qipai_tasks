package com.anbang.qipai.tasks.msg.receiver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.msg.channel.MembersSink;
import com.anbang.qipai.tasks.plan.domain.MemberDbo;
import com.anbang.qipai.tasks.plan.service.MemberService;
import com.google.gson.Gson;

@EnableBinding(MembersSink.class)
public class MembersMsgReceiver {
	
	@Autowired
	private MemberService memberService;
	
	private Gson gson = new Gson();
	
	@StreamListener(MembersSink.MEMBERS)
	public void createMembner(Object payload) {
		Map map = gson.fromJson(payload.toString(),Map.class);
		String msg = (String) map.get("msg");
		if ("newMember".equals(msg)) {
			Map memberMap = (Map) map.get("data");
			MemberDbo member = gson.fromJson(gson.toJson(memberMap),MemberDbo.class);
			memberService.addMember(member);
		}
		if ("updateMember".equals(msg)) {
			Map memberMap = (Map) map.get("data");
			MemberDbo member = gson.fromJson(gson.toJson(memberMap),MemberDbo.class);
			memberService.updateMemberDbo(member);
		}
	}

}
