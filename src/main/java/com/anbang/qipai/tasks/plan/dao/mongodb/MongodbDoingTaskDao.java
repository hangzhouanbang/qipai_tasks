package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.dao.DoingTaskDao;
import com.anbang.qipai.tasks.plan.domain.DoingTask;

@Component
public class MongodbDoingTaskDao implements DoingTaskDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<DoingTask> findAllDoingTaskByMemberId(String memberId) {
		return mongoTemplate.find(new Query(Criteria.where("memberId").is(memberId)), DoingTask.class);
	}

	@Override
	public List<DoingTask> findAllDoingTask() {
		return mongoTemplate.findAll(DoingTask.class);
	}

	@Override
	public void addDoingTask(DoingTask doingTask) {
		mongoTemplate.insert(doingTask);
	}

	@Override
	public List<DoingTask> findDoingTaskByMemberIdAndType(String memberId, String type) {
		Query query = new Query(Criteria.where("memberId").is(memberId));
		query.addCriteria(Criteria.where("type").is(type));
		return mongoTemplate.find(query, DoingTask.class);
	}

	@Override
	public DoingTask findDoingTaskById(String doingTaskId) {
		Query query = new Query(Criteria.where("id").is(doingTaskId));
		return mongoTemplate.findOne(query, DoingTask.class);
	}

}
