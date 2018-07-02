package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.anbang.qipai.tasks.plan.dao.TaskDocumentHistoryDao;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

public class MongodbTaskDocumentHistoryDao implements TaskDocumentHistoryDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public long getAmount(TaskDocumentHistory taskDoc) {
		Query query = new Query(Criteria.where("state").is("RELEASE"));
		if (taskDoc.getName() != null) {
			query.addCriteria(Criteria.where("name").regex(taskDoc.getName()));
		}
		if (taskDoc.getTaskType() != null) {
			query.addCriteria(Criteria.where("taskType").is(taskDoc.getTaskType()));
		}
		if (taskDoc.getPromulgator() != null) {
			query.addCriteria(Criteria.where("promulgator").is(taskDoc.getPromulgator()));
		}
		return mongoTemplate.count(query, TaskDocumentHistory.class);
	}

	@Override
	public List<TaskDocumentHistory> findTaskDocuments(int page, int size, TaskDocumentHistory taskDoc) {
		Query query = new Query(Criteria.where("state").is("RELEASE"));
		if (taskDoc.getName() != null) {
			query.addCriteria(Criteria.where("name").regex(taskDoc.getName()));
		}
		if (taskDoc.getTaskType() != null) {
			query.addCriteria(Criteria.where("taskType").is(taskDoc.getTaskType()));
		}
		if (taskDoc.getPromulgator() != null) {
			query.addCriteria(Criteria.where("promulgator").is(taskDoc.getPromulgator()));
		}
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, TaskDocumentHistory.class);
	}

	@Override
	public void add(TaskDocumentHistory taskDocumentHistory) {
		mongoTemplate.insert(taskDocumentHistory);
	}

	@Override
	public void updateState(String taskId, String state) {
		Query query = new Query(Criteria.where("id").is(taskId));
		Update update = new Update();
		update.set("state", state);
		mongoTemplate.updateFirst(query, update, TaskDocumentHistory.class);
	}

}
