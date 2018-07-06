package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.dao.TaskDao;
import com.anbang.qipai.tasks.plan.domain.Task;

@Component
public class MongodbTaskDao implements TaskDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Task> findTaskByMemberId(String memberId) {
		return mongoTemplate.find(new Query(Criteria.where("memberId").is(memberId)), Task.class);
	}

	@Override
	public List<Task> findAllTask() {
		return mongoTemplate.findAll(Task.class);
	}

	@Override
	public void addTask(Task task) {
		mongoTemplate.insert(task);
	}

	@Override
	public List<Task> findTaskByMemberIdAndType(String memberId, String type) {
		Query query = new Query(Criteria.where("memberId").is(memberId));
		query.addCriteria(Criteria.where("type").is(type));
		return mongoTemplate.find(query, Task.class);
	}

	@Override
	public Task findTaskById(String taskId) {
		Query query = new Query(Criteria.where("id").is(taskId));
		return mongoTemplate.findOne(query, Task.class);
	}

}
