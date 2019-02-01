package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.bean.MemberLoginRecord;
import com.anbang.qipai.tasks.plan.dao.MemberLoginRecordDao;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;

@Component
public class MongodbMemberLoginRecordDao implements MemberLoginRecordDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(MemberLoginRecord record) {
		mongoTemplate.insert(record);
	}

	@Override
	public void updateOnlineTimeById(String id, long onlineTime) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("onlineTime", onlineTime);
		mongoTemplate.updateFirst(query, update, MemberLoginRecord.class);
	}

	@Override
	public MemberLoginRecord findRecentRecordByMemberId(String memberId) {
		Query query = new Query(Criteria.where("memberId").is(memberId));
		// 需要建立索引
		Sort sort = new Sort(new Order(Direction.DESC, "loginTime"));
		query.with(sort);
		return mongoTemplate.findOne(query, MemberLoginRecord.class);
	}

	@Override
	public long countLoginRecordByMemberId(String memberId) {
		Query query = new Query(Criteria.where("memberId").is(memberId));
		return mongoTemplate.count(query, MemberLoginRecord.class);
	}

	@Override
	public int countMemberNumByLoginIp(String loginIp) {
		List<DBObject> pipeline = new ArrayList<>();
		BasicDBObject match = new BasicDBObject();
		match.put("loginIp", loginIp);
		DBObject queryMatch = new BasicDBObject("$match", match);
		pipeline.add(queryMatch);

		BasicDBObject group = new BasicDBObject();
		group.put("_id", "$memberId");
		DBObject queryGroup = new BasicDBObject("$group", group);
		pipeline.add(queryGroup);

		DBObject count = new BasicDBObject("$count", "num");
		pipeline.add(count);
		Cursor cursor = mongoTemplate.getCollection("memberLoginRecord").aggregate(pipeline,
				AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
		try {
			return (int) cursor.next().get("num");
		} catch (Exception e) {
			return 0;
		}
	}

}
