package com.anbang.qipai.tasks.msg.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.msg.channel.sink.MembersSink;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.service.MemberService;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.google.gson.Gson;

@EnableBinding(MembersSink.class)
public class MembersMsgReceiver {

	@Autowired
	private MemberService memberService;

	@Autowired
	private TaskService taskService;

	private Gson gson = new Gson();

	@StreamListener(MembersSink.MEMBERS)
	public void recordMember(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		MemberDbo member = gson.fromJson(json, MemberDbo.class);
		if ("newMember".equals(msg)) {
			memberService.addMember(member);
		}
		if ("memberOrder delive".equals(msg) || "recharge vip".equals(msg) || "update member vip".equals(msg)) {
			memberService.updateVip(member.getId(), member.isVip());
			if (member.isVip()) {// 只有会员才更新任务
				taskService.updateTask(member.getId(), "成为会员", 1);
			}
		}
		if ("update member info".equals(msg)) {
			memberService.updateBaseInfo(member.getId(), member.getNickname(), member.getHeadimgurl());
		}
	}

}
