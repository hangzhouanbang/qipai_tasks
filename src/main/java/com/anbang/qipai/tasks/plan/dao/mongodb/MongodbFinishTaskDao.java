package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.bean.FinishedTask;
import com.anbang.qipai.tasks.plan.dao.FinishTaskDao;

@Component
public class MongodbFinishTaskDao implements FinishTaskDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addFinishTask(FinishedTask finishTask) {
		mongoTemplate.insert(finishTask);
	}

	@Override
	public List<FinishedTask> findFinishTaskByMemberId(String memberId) {
		return mongoTemplate.find(new Query(Criteria.where("memberId").is(memberId)), FinishedTask.class);
	}

}
