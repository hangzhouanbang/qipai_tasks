package com.anbang.qipai.tasks.plan.bean;

import java.util.Map;

public interface ITarget {

	void init(TaskDocumentHistory task);

	void reset(Task task);

	void updateTask(Task task, Map<String, Object> params);
}
