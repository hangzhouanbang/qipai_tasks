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

	@Override
	public FinishedTask findFinishTaskByMemberIdAndTaskId(String memberId, String taskId) {
		return mongoTemplate.findOne(new Query(Criteria.where("memberId").is(memberId).and("taskId").is(taskId)),
				FinishedTask.class);
	}

	@Override
	public FinishedTask findFinishTaskById(String id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), FinishedTask.class);
	}

	@Override
	public void remove(String id) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)), FinishedTask.class);
	}

}
