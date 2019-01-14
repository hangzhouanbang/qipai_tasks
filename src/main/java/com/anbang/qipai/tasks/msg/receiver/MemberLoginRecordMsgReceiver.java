package com.anbang.qipai.tasks.msg.receiver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.msg.channel.sink.MemberLoginRecordSink;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.MemberLoginRecord;
import com.anbang.qipai.tasks.plan.service.MemberLoginRecordService;
import com.anbang.qipai.tasks.plan.service.MemberService;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.google.gson.Gson;

@EnableBinding(MemberLoginRecordSink.class)
public class MemberLoginRecordMsgReceiver {

	@Autowired
	private MemberLoginRecordService memberLoginRecordService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private TaskService taskService;

	private Gson gson = new Gson();

	@StreamListener(MemberLoginRecordSink.MEMBERLOGINRECORD)
	public void memberLoginRecord(CommonMO mo) {
		String msg = mo.getMsg();
		Map map = (Map) mo.getData();
		if ("member login".equals(msg)) {
			String json = gson.toJson(map.get("record"));
			MemberLoginRecord record = gson.fromJson(json, MemberLoginRecord.class);
			MemberDbo member = memberService.findMemberById(record.getMemberId());
			record.setNickname(member.getNickname());
			memberLoginRecordService.save(record);
		}
		if ("update member onlineTime".equals(msg)) {
			String json = gson.toJson(mo.getData());
			MemberLoginRecord record = gson.fromJson(json, MemberLoginRecord.class);
			memberLoginRecordService.updateOnlineTimeById(record.getId(), record.getOnlineTime());
		}
		if ("member logout".equals(msg)) {
			// String memberId = (String) map.get("memberId");
			// MemberLoginRecord record =
			// memberLoginRecordService.findRecentRecordByMemberId(memberId);
			// long onlineTime = record.getOnlineTime();
		}
	}
}
