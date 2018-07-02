package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.anbang.qipai.tasks.plan.dao.TaskDocumentDao;
import com.anbang.qipai.tasks.plan.domain.TaskDocument;

public class MongodbTaskDocumentDao implements TaskDocumentDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<TaskDocument> findTaskDocuments(int page, int size, TaskDocument taskDoc) {
		Query query = new Query();
		if (taskDoc.getName() != null) {
			query.addCriteria(Criteria.where("name").regex(taskDoc.getName()));
		}
		if (taskDoc.getTaskType() != null) {
			query.addCriteria(Criteria.where("taskType").is(taskDoc.getTaskType()));
		}
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, TaskDocument.class);
	}

	@Override
	public long getAmount(TaskDocument taskDoc) {
		Query query = new Query();
		if (taskDoc.getName() != null) {
			query.addCriteria(Criteria.where("name").regex(taskDoc.getName()));
		}
		if (taskDoc.getTaskType() != null) {
			query.addCriteria(Criteria.where("taskType").is(taskDoc.getTaskType()));
		}
		return mongoTemplate.count(query, TaskDocument.class);
	}

	@Override
	public TaskDocument findDocumentById(String documentId) {
		Query query = new Query(Criteria.where("id").is(documentId));
		return mongoTemplate.findOne(query, TaskDocument.class);
	}

	@Override
	public void addTaskDocument(TaskDocument taskDoc) {
		mongoTemplate.insert(taskDoc);
	}

	@Override
	public void deleteTaskDocument(String[] taskIds) {
		Object[] ids = taskIds;
		Query query = new Query(Criteria.where("id").in(ids));
		mongoTemplate.remove(query, TaskDocument.class);
	}

	@Override
	public void updateTaskDocument(TaskDocument taskDoc) {
		Query query = new Query(Criteria.where("id").is(taskDoc.getId()));
		Update update = new Update();
		if (taskDoc.getCriterions() != null) {
			update.set("criterions", taskDoc.getCriterions());
		}
		if (taskDoc.getDesc() != null) {
			update.set("desc", taskDoc.getDesc());
		}
		if (taskDoc.getName() != null) {
			update.set("name", taskDoc.getName());
		}
		if (taskDoc.getReward() != null) {
			update.set("reward", taskDoc.getReward());
		}
		if (taskDoc.getTaskType() != null) {
			update.set("taskType", taskDoc.getTaskType());
		}
		mongoTemplate.updateFirst(query, update, TaskDocument.class);
	}

}
