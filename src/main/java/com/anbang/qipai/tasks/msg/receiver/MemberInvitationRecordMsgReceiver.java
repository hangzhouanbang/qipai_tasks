package com.anbang.qipai.tasks.msg.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.msg.channel.sink.MemberLoginRecordSink;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.bean.MemberInvitationRecord;
import com.anbang.qipai.tasks.plan.service.MemberInvitationRecordService;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.google.gson.Gson;

@EnableBinding(MemberLoginRecordSink.class)
public class MemberInvitationRecordMsgReceiver {
	@Autowired
	private MemberInvitationRecordService memberInvitationRecordService;

	@Autowired
	private TaskService taskService;

	private Gson gson = new Gson();

	@StreamListener(MemberLoginRecordSink.MEMBERLOGINRECORD)
	public void memberLoginRecord(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		if ("new record".equals(msg)) {
			MemberInvitationRecord record = gson.fromJson(json, MemberInvitationRecord.class);
			memberInvitationRecordService.insertMemberInvitationRecord(record);
			taskService.updateTask(record.getMemberId(), "邀请新玩家", 1);
		}
	}
}
