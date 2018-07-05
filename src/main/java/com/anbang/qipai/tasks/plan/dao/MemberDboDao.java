package com.anbang.qipai.tasks.plan.dao;

import com.anbang.qipai.tasks.plan.domain.MemberDbo;

public interface MemberDboDao {

	void addMemberDbo(MemberDbo member);

	boolean updateMemberDbo(MemberDbo member);

	MemberDbo findMemberById(String memberId);
}
