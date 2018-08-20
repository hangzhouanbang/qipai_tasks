package com.anbang.qipai.tasks.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.dao.MemberDboDao;

@Service
public class MemberService {

	@Autowired
	private MemberDboDao memberDboDao;

	public void addMember(MemberDbo memberDbo) {
		memberDbo.setReleaseTime(0L);
		memberDboDao.addMemberDbo(memberDbo);
	}

	public boolean updateVip(String memberId, boolean vip) {
		return memberDboDao.updateVip(memberId, vip);
	}

	public boolean updateLastLoginTime(String memberId, long lastLoginTime) {
		return memberDboDao.updateLastLoginTime(memberId, lastLoginTime);
	}

	public boolean updateOnlineTime(String memberId, long onLineTime) {
		return memberDboDao.updateOnlineTime(memberId, onLineTime);
	}
}
