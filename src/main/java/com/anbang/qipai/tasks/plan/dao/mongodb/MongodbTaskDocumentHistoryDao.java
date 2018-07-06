package com.anbang.qipai.tasks.plan.dao.mongodb;

import java.util.List;

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
	public boolean updateState(String[] taskIds, int state) {
		Object[] ids = taskIds;
		Query query = new Query(Criteria.where("id").in(ids));
		Update update = new Update();
		update.set("state", state);
		WriteResult result = mongoTemplate.updateMulti(query, update, TaskDocumentHistory.class);
		return result.getN() <= taskIds.length;
	}

	@Override
	public TaskDocumentHistory findTaskById(String taskId) {
		Query query = new Query(Criteria.where("id").is(taskId));
		return mongoTemplate.findOne(query, TaskDocumentHistory.class);
	}

	@Override
	public long getAmount(long releaseTime) {
		Query query = new Query(Criteria.where("releaseTime").gt(releaseTime));
		query.addCriteria(Criteria.where("state").is(1));
		return mongoTemplate.count(query, TaskDocumentHistory.class);
	}

	@Override
	public List<TaskDocumentHistory> findTaskByReleaseTime(int page, int size, long releaseTime) {
		Query query = new Query(Criteria.where("releaseTime").gt(releaseTime));
		query.addCriteria(Criteria.where("state").is(1));
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, TaskDocumentHistory.class);
	}

}
