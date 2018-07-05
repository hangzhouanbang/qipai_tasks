package com.anbang.qipai.tasks.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.plan.dao.MemberDboDao;
import com.anbang.qipai.tasks.plan.domain.MemberDbo;

@Service
public class MemberService {
	
	@Autowired
	private MemberDboDao memberDboDao;
	
	public void addMember(MemberDbo memberDbo) {
		memberDboDao.addMemberDbo(memberDbo);
	}
	
	public void updateMemberDbo(MemberDbo memberDbo) {
		memberDboDao.updateMemberDbo(memberDbo);
	}
}
