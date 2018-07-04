package com.anbang.qipai.tasks.config;

import java.util.Map;

public class TaskTemplate {
	private Integer taskType;
	private String taskName;
	private Map<String, String> criterions;

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Map<String, String> getCriterions() {
		return criterions;
	}

	public void setCriterions(Map<String, String> criterions) {
		this.criterions = criterions;
	}

}
