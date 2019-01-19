package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskType;
import com.anbang.qipai.tasks.plan.dao.TaskDao;

@Component
public class MongodbTaskDao implements TaskDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Task> findTaskByMemberId(String memberId) {
		return mongoTemplate.find(new Query(Criteria.where("memberId").is(memberId)), Task.class);
	}

	@Override
	public List<Task> findTasksByType(int page, int size, TaskType taskType) {
		Query query = new Query(Criteria.where("taskType").is(taskType));
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
		query.with(new Sort(Direction.ASC, "weight"));
		return mongoTemplate.find(query, Task.class);
	}

	@Override
	public Task findTaskById(String taskId) {
		Query query = new Query(Criteria.where("id").is(taskId));
		return mongoTemplate.findOne(query, Task.class);
	}

	@Override
	public void updateTask(Task task) {
		Query query = new Query(Criteria.where("id").is(task.getId()));
		Update update = new Update();
		update.set("menu", task.getMenu());
		update.set("taskState", task.getTaskState());
		update.set("finishNum", task.getFinishNum());
		update.set("target", task.getTarget());
		mongoTemplate.updateFirst(query, update, Task.class);
	}

	@Override
	public long getAmountByType(TaskType taskType) {
		Query query = new Query(Criteria.where("taskType").is(taskType));
		return mongoTemplate.count(query, Task.class);
	}

	@Override
	public void deleteTaskById(String taskId) {
		Query query = new Query(Criteria.where("id").is(taskId));
		mongoTemplate.remove(query, Task.class);
	}

	@Override
	public Task findTaskByMemberIdAndTaskId(String memberId, String taskId) {
		Query query = new Query(Criteria.where("memberId").is(memberId));
		query.addCriteria(Criteria.where("taskId").is(taskId));
		return mongoTemplate.findOne(query, Task.class);
	}

	@Override
	public List<Task> findTaskByMemberIdAndTaskName(String memberId, String taskName) {
		Query query = new Query(Criteria.where("memberId").is(memberId));
		query.addCriteria(Criteria.where("taskName").is(taskName));
		return mongoTemplate.find(query, Task.class);
	}

}
