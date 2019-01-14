package com.anbang.qipai.tasks.plan.dao;

import com.anbang.qipai.tasks.plan.bean.MemberInvitationRecord;

public interface MemberInvitationRecordDao {

	void insert(MemberInvitationRecord record);

	long countInvitationByMemberId(String memberId);
}
