package com.anbang.qipai.tasks.plan.domain;

/**完成的任务
 * **/
public class FinishTask {

	private String id;
	
	private String memberId;//会员id
	
	private long finishTime;//完成时间
	
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

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	
}