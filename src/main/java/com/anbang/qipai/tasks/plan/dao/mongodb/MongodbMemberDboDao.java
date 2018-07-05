package com.anbang.qipai.tasks.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.dao.MemberDboDao;
import com.anbang.qipai.tasks.plan.domain.MemberDbo;
import com.mongodb.WriteResult;

@Component
public class MongodbMemberDboDao implements MemberDboDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addMemberDbo(MemberDbo member) {
		mongoTemplate.insert(member);
	}

	@Override
	public boolean updateMemberDbo(MemberDbo member) {
		Query query = new Query(Criteria.where("id").is(member.getId()));
		Update update = new Update();
		update.set("vip",member.getVip());
		update.set("createTime",member.getCreateTime());
		update.set("lastLoginTime",member.getLastLoginTime());
		update.set("onlineTime",member.getOnlineTime());
		WriteResult result = mongoTemplate.updateFirst(query, update, MemberDbo.class);
		return result.getN() > 0;
	}

	@Override
	public MemberDbo findMemberById(String memberId) {
		Query query = new Query(Criteria.where("id").is(memberId));
		return mongoTemplate.findOne(query, MemberDbo.class);
	}

	@Override
	public boolean updateMemberDboTask(MemberDbo member) {
		Query query = new Query(Criteria.where("id").is(member.getId()));
		Update update = new Update();
		WriteResult result = mongoTemplate.updateFirst(query, update, MemberDbo.class);
		return result.getN() > 0;
	}

}