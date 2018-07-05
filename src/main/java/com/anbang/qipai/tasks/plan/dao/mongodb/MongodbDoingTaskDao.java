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
public class MongodbDoingTaskDao implements DoingTaskDao{
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<DoingTask> findAllDoingTaskByMemberId(String memberId) {
		return mongoTemplate.find(new Query(Criteria.where("memberId").is(memberId)),DoingTask.class);
	}

	@Override
	public List<DoingTask> findAllDoingTask() {
		return mongoTemplate.findAll(DoingTask.class);
	}

	@Override
	public void addDoingTask(DoingTask doingTask) {
		mongoTemplate.insert(doingTask);
	}
	
	
	
	

}
