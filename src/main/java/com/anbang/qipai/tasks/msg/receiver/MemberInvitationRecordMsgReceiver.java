package com.anbang.qipai.tasks.msg.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.config.MemberInvitationRecordState;
import com.anbang.qipai.tasks.msg.channel.sink.MemberInvitationRecordSink;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.bean.MemberInvitationRecord;
import com.anbang.qipai.tasks.plan.service.MemberInvitationRecordService;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.google.gson.Gson;

@EnableBinding(MemberInvitationRecordSink.class)
public class MemberInvitationRecordMsgReceiver {
	@Autowired
	private MemberInvitationRecordService memberInvitationRecordService;

	@Autowired
	private TaskService taskService;

	private Gson gson = new Gson();

	@StreamListener(MemberInvitationRecordSink.MEMBERINVITATIONRECORD)
	public void memberLoginRecord(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		if ("new record".equals(msg)) {
			MemberInvitationRecord record = gson.fromJson(json, MemberInvitationRecord.class);
			memberInvitationRecordService.insertMemberInvitationRecord(record);
		}
		if ("update record".equals(msg)) {
			MemberInvitationRecord record = gson.fromJson(json, MemberInvitationRecord.class);
			memberInvitationRecordService.updateMemberInvitationRecordState(record.getId(), record.getState());
			if (record.getState().equals(MemberInvitationRecordState.SUCCESS)) {
				taskService.updateTask(record.getMemberId(), "邀请新玩家", 1);
			}
		}
	}
}
