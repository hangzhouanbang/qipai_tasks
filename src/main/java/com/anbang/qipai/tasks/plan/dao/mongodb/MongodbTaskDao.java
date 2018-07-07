package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.dao.TaskDao;
import com.anbang.qipai.tasks.plan.domain.Task;
import com.mongodb.WriteResult;

@Component
public class MongodbTaskDao implements TaskDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Task> findTaskByMemberId(String memberId) {
		return mongoTemplate.find(new Query(Criteria.where("memberId").is(memberId)), Task.class);
	}

	@Override
	public List<Task> findTasksByType(int page, int size, String type) {
		Query query = new Query(Criteria.where("type").is(type));
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, Task.class);
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

	@Override
	public boolean updateTask(Task task) {
		Query query = new Query(Criteria.where("id").is(task.getId()));
		Update update = new Update();
		update.set("taskState", task.getTaskState());
		update.set("finishNum", task.getFinishNum());
		update.set("target", task.getTarget());
		WriteResult result = mongoTemplate.updateFirst(query, update, Task.class);
		return result.getN() > 0;
	}

	@Override
	public long getAmountByType(String type) {
		Query query = new Query(Criteria.where("type").is(type));
		return mongoTemplate.count(query, Task.class);
	}

	@Override
	public boolean deleteTaskById(String taskId) {
		Query query = new Query(Criteria.where("id").is(taskId));
		WriteResult result = mongoTemplate.remove(query, Task.class);
		return result.getN() > 0;
	}

}
