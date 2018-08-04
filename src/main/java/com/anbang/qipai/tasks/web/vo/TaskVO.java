package com.anbang.qipai.tasks.web.vo;

import java.util.List;

import com.anbang.qipai.tasks.plan.bean.Task;

public class TaskVO {

	private String type;
	private List<Task> taskList;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

}
