package com.anbang.qipai.tasks.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.dao.MemberDboDao;
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
	public MemberDbo findMemberById(String memberId) {
		Query query = new Query(Criteria.where("id").is(memberId));
		return mongoTemplate.findOne(query, MemberDbo.class);
	}

	@Override
	public boolean updateVip(String memberId, boolean vip) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("vip", vip);
		WriteResult result = mongoTemplate.updateFirst(query, update, MemberDbo.class);
		return result.getN() > 0;
	}

	@Override
	public boolean updateLastLoginTime(String memberId, long lastLoginTime) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("lastLoginTime", lastLoginTime);
		WriteResult result = mongoTemplate.updateFirst(query, update, MemberDbo.class);
		return result.getN() > 0;
	}

	@Override
	public boolean updateOnlineTime(String memberId, long onLineTime) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("onLineTime", onLineTime);
		WriteResult result = mongoTemplate.updateFirst(query, update, MemberDbo.class);
		return result.getN() > 0;
	}

	@Override
	public boolean updateReleaseTime(String memberId, long releaseTime) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("releaseTime", releaseTime);
		WriteResult result = mongoTemplate.updateFirst(query, update, MemberDbo.class);
		return result.getN() > 0;
	}

}
