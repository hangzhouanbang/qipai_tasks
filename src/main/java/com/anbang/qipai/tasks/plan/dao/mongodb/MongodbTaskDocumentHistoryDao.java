package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistoryState;
import com.anbang.qipai.tasks.plan.dao.TaskDocumentHistoryDao;

@Component
public class MongodbTaskDocumentHistoryDao implements TaskDocumentHistoryDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addTaskDocumentHistory(TaskDocumentHistory taskDocumentHistory) {
		mongoTemplate.insert(taskDocumentHistory);
	}

	@Override
	public void updateState(String taskId, TaskDocumentHistoryState state) {
		Query query = new Query(Criteria.where("id").is(taskId));
		Update update = new Update();
		update.set("state", state);
		mongoTemplate.updateMulti(query, update, TaskDocumentHistory.class);
	}

	@Override
	public TaskDocumentHistory findTaskById(String taskId) {
		Query query = new Query(Criteria.where("id").is(taskId));
		return mongoTemplate.findOne(query, TaskDocumentHistory.class);
	}

	@Override
	public long getAmountByState(TaskDocumentHistoryState state) {
		Query query = new Query();
		query.addCriteria(Criteria.where("state").is(state));
		return mongoTemplate.count(query, TaskDocumentHistory.class);
	}

	@Override
	public List<TaskDocumentHistory> findTaskByState(TaskDocumentHistoryState state) {
		Query query = new Query();
		query.addCriteria(Criteria.where("state").is(state));
		return mongoTemplate.find(query, TaskDocumentHistory.class);
	}

}
