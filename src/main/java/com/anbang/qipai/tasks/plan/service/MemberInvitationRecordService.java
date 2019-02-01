package com.anbang.qipai.tasks.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.plan.bean.MemberInvitationRecord;
import com.anbang.qipai.tasks.plan.dao.MemberInvitationRecordDao;

@Service
public class MemberInvitationRecordService {

	@Autowired
	private MemberInvitationRecordDao memberInvitationRecordDao;

	public void insertMemberInvitationRecord(MemberInvitationRecord record) {
		memberInvitationRecordDao.insert(record);
	}

	public void updateMemberInvitationRecordState(String id, String state) {
		memberInvitationRecordDao.updateState(id, state);
	}

	public int countInvitationByMemberId(String memberId) {
		return (int) memberInvitationRecordDao.countInvitationByMemberId(memberId);
	}

	public MemberInvitationRecord findMemberInvitationRecordByInvitationMemberId(String invitationMemberId) {
		return memberInvitationRecordDao.findByInvitationMemberId(invitationMemberId);
	}
}
