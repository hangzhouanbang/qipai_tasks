package com.anbang.qipai.tasks.plan.bean;

import com.anbang.qipai.tasks.config.TargetType;

public class TestDbo {
	private String id;
	private TaskAction taskAction;
	private TargetType targetType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TaskAction getTaskAction() {
		return taskAction;
	}

	public void setTaskAction(TaskAction taskAction) {
		this.taskAction = taskAction;
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}
}
