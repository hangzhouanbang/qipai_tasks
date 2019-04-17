package com.anbang.qipai.tasks.plan.dao.mongodb;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.dao.MemberDboDao;

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
	public void updateVip(String memberId, boolean vip) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("vip", vip);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateLastLoginTime(String memberId, long lastLoginTime) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("lastLoginTime", lastLoginTime);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateOnlineTime(String memberId, long onLineTime) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("onLineTime", onLineTime);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateBaseInfo(String memberId, String nickname, String headimgurl, String reqIP) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("nickname", nickname);
		update.set("headimgurl", headimgurl);
		if (!StringUtils.isBlank(reqIP)) {
			update.set("reqIP", reqIP);
		}
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

}
