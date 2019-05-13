package com.anbang.qipai.tasks.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.config.TargetType;
import com.anbang.qipai.tasks.plan.bean.TaskAction;
import com.anbang.qipai.tasks.plan.bean.TestDbo;

@Component
public class TestDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	public void test() {
		TestDbo dbo = new TestDbo();
		dbo.setTaskAction(TaskAction.REWARD);
		dbo.setTargetType(TargetType.DuijuGame);
		mongoTemplate.insert(dbo);
	}
}
