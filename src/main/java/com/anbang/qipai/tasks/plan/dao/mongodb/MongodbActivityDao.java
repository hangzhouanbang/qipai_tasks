package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.config.ActivityState;
import com.anbang.qipai.tasks.plan.bean.Activity;
import com.anbang.qipai.tasks.plan.dao.ActivityDao;

@Component
public class MongodbActivityDao implements ActivityDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addActivity(Activity activity) {
		mongoTemplate.insert(activity);
	}

	@Override
	public void updateActivityStateById(String activityId, String state) {
		Query query = new Query(Criteria.where("id").is(activityId));
		Update update = new Update();
		update.set("state", state);
		mongoTemplate.updateFirst(query, update, Activity.class);
	}

	@Override
	public List<Activity> findActivity() {
		Query query = new Query(Criteria.where("state").is(ActivityState.START));
		return mongoTemplate.find(query, Activity.class);
	}

	@Override
	public Activity findActivityById(String activityId) {
		Query query = new Query(Criteria.where("id").is(activityId));
		return mongoTemplate.findOne(query, Activity.class);
	}

}
