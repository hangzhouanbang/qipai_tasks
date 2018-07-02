package com.anbang.qipai.tasks.plan.domain;

import java.util.Map;

public class TaskDocument {
	private String id;
	private String name;
	private String desc;
	private String taskType;
	private Map<String, String> criterions;
	private Map<String, String> reward;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Map<String, String> getCriterions() {
		return criterions;
	}

	public void setCriterions(Map<String, String> criterions) {
		this.criterions = criterions;
	}

	public Map<String, String> getReward() {
		return reward;
	}

	public void setReward(Map<String, String> reward) {
		this.reward = reward;
	}

}
