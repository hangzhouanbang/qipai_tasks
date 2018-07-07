package com.anbang.qipai.tasks.plan.dao;

import com.anbang.qipai.tasks.plan.domain.MemberDbo;

public interface MemberDboDao {

	void addMemberDbo(MemberDbo member);

	boolean updateVip(String memberId, boolean vip);

	boolean updateLastLoginTime(String memberId, long lastLoginTime);

	boolean updateOnlineTime(String memberId, long onLineTime);

	boolean updateReleaseTime(String memberId, long releaseTime);

	MemberDbo findMemberById(String memberId);
}
