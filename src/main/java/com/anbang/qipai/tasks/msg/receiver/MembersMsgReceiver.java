package com.anbang.qipai.tasks.msg.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.msg.channel.MembersSink;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.service.MemberService;
import com.google.gson.Gson;

@EnableBinding(MembersSink.class)
public class MembersMsgReceiver {

	@Autowired
	private MemberService memberService;

	private Gson gson = new Gson();

	@StreamListener(MembersSink.MEMBERS)
	public void recordMember(CommonMO mo) {
		String json = gson.toJson(mo.getData());
		MemberDbo member = gson.fromJson(json, MemberDbo.class);
		if ("newMember".equals(mo.getMsg())) {
			memberService.addMember(member);
		}
		if ("update member vip".equals(mo.getMsg())) {
			memberService.updateVip(member.getId(), member.isVip());
		}
	}

}
