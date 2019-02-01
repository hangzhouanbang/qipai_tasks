package com.anbang.qipai.tasks.plan.dao;

import com.anbang.qipai.tasks.plan.bean.MemberInvitationRecord;

public interface MemberInvitationRecordDao {

	void insert(MemberInvitationRecord record);

	void updateState(String id, String state);

	long countInvitationByMemberId(String memberId);

	MemberInvitationRecord findByInvitationMemberId(String invitationMemberId);
}
