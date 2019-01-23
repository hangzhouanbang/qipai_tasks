package com.anbang.qipai.tasks.plan.dao;

import com.anbang.qipai.tasks.plan.bean.MemberLoginRecord;

public interface MemberLoginRecordDao {

	void save(MemberLoginRecord record);

	void updateOnlineTimeById(String id, long onlineTime);

	MemberLoginRecord findRecentRecordByMemberId(String memberId);

	long countLoginRecordByMemberId(String memberId);
}
