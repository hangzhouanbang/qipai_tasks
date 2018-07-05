package com.anbang.qipai.tasks.plan.domain;

public class DoingTask {
	
	private String id;
	
	private String memberId;//会员id
	
	private Task task;//任务信息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	

}
