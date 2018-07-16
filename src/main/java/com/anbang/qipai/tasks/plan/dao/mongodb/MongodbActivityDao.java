package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.dao.ActivityDao;
import com.anbang.qipai.tasks.plan.domain.Activity;
import com.mongodb.WriteResult;

@Component
public class MongodbActivityDao implements ActivityDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addActivity(Activity activity) {
		mongoTemplate.insert(activity);
	}

	@Override
	public boolean updateActivityStateById(String activityId, int state) {
		Query query = new Query(Criteria.where("id").is(activityId));
		Update update = new Update();
		update.set("state", state);
		WriteResult result = mongoTemplate.updateFirst(query, update, Activity.class);
		return result.getN() > 0;
	}

	@Override
	public List<Activity> findActivity() {
		Query query = new Query(Criteria.where("state").is(1));
		return mongoTemplate.find(query, Activity.class);
	}

	@Override
	public Activity findActivityById(String activityId) {
		Query query = new Query(Criteria.where("id").is(activityId));
		return mongoTemplate.findOne(query, Activity.class);
	}

}
