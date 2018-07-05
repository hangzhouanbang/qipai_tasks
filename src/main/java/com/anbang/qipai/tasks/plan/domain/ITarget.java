package com.anbang.qipai.tasks.plan.domain;

import java.util.Map;

public interface ITarget {

	void init(TaskDocumentHistory task);

	void updateTask(Task task, Map<String, Object> params);
}
