package com.anbang.qipai.tasks.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.bean.MemberInvitationRecord;
import com.anbang.qipai.tasks.plan.dao.MemberInvitationRecordDao;

@Component
public class MongodbMemberInvitationRecordDao implements MemberInvitationRecordDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void insert(MemberInvitationRecord record) {
		mongoTemplate.insert(record);
	}

	@Override
	public long countInvitationByMemberId(String memberId) {
		Query query = new Query(Criteria.where("memberId").is(memberId));
		return mongoTemplate.count(query, MemberInvitationRecord.class);
	}

	@Override
	public MemberInvitationRecord findByInvitationMemberId(String invitationMemberId) {
		Query query = new Query(Criteria.where("invitationMemberId").is(invitationMemberId));
		return mongoTemplate.findOne(query, MemberInvitationRecord.class);
	}

	@Override
	public void updateState(String id, String state) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("state", state);
		mongoTemplate.updateFirst(query, update, MemberInvitationRecord.class);
	}

}
