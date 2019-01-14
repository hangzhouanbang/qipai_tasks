package com.anbang.qipai.tasks.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.bean.MemberInvitationRecord;
import com.anbang.qipai.tasks.plan.dao.MemberInvitationRecordDao;

@Component
public class MongodbMemberInvitationRecordDao implements MemberInvitationRecordDao {

	@Autowired
	private MongoTemplate mongoTempalte;

	@Override
	public void insert(MemberInvitationRecord record) {
		mongoTempalte.insert(record);
	}

	@Override
	public long countInvitationByMemberId(String memberId) {
		Query query = new Query(Criteria.where("memberId").is(memberId));
		return mongoTempalte.count(query, MemberInvitationRecord.class);
	}

}
