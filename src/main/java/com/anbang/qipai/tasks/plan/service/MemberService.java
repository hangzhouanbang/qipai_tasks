package com.anbang.qipai.tasks.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.dao.MemberDboDao;

@Service
public class MemberService {

	@Autowired
	private MemberDboDao memberDboDao;

	public MemberDbo findMemberById(String memberId) {
		return memberDboDao.findMemberById(memberId);
	}

	public void addMember(MemberDbo memberDbo) {
		memberDboDao.addMemberDbo(memberDbo);
	}

	public void updateVip(String memberId, boolean vip) {
		memberDboDao.updateVip(memberId, vip);
	}

	public void updateLastLoginTime(String memberId, long lastLoginTime) {
		memberDboDao.updateLastLoginTime(memberId, lastLoginTime);
	}

	public void updateOnlineTime(String memberId, long onLineTime) {
		memberDboDao.updateOnlineTime(memberId, onLineTime);
	}

	public void updateBaseInfo(String memberId, String nickname, String headimgurl) {
		memberDboDao.updateBaseInfo(memberId, nickname, headimgurl);
	}
}
