package com.anbang.qipai.tasks.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.plan.dao.TaskDocumentHistoryDao;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;
import com.mongodb.WriteResult;

@Component
public class MongodbTaskDocumentHistoryDao implements TaskDocumentHistoryDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addTaskDocumentHistory(TaskDocumentHistory taskDocumentHistory) {
		mongoTemplate.insert(taskDocumentHistory);
	}

	@Override
	public boolean updateState(String taskId, int state) {
		Query query = new Query(Criteria.where("id").is(taskId));
		Update update = new Update();
		update.set("state", state);
		WriteResult result = mongoTemplate.updateFirst(query, update, TaskDocumentHistory.class);
		return result.getN() > 0;
	}

	@Override
	public TaskDocumentHistory findTaskById(String taskId) {
		Query query = new Query(Criteria.where("id").is(taskId));
		return mongoTemplate.findOne(query, TaskDocumentHistory.class);
	}

}
