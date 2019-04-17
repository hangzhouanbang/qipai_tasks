package com.anbang.qipai.tasks.plan.dao;

import com.anbang.qipai.tasks.plan.bean.MemberDbo;

public interface MemberDboDao {

	void addMemberDbo(MemberDbo member);

	void updateVip(String memberId, boolean vip);

	void updateLastLoginTime(String memberId, long lastLoginTime);

	void updateOnlineTime(String memberId, long onLineTime);

	void updateBaseInfo(String memberId, String nickname, String headimgurl, String reqIP);

	MemberDbo findMemberById(String memberId);
}
