package com.anbang.qipai.tasks.plan.domain;

public class Task {
	private String id;
	private String taskId;// 任务历史id
	private String memberId;// 会员id
	private String type;// 任务类型
	private String rewardType;// 奖励类型
	private Integer rewardNum;
	private String taskState;// 任务状态
	private ITarget target;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public Integer getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(Integer rewardNum) {
		this.rewardNum = rewardNum;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public ITarget getTarget() {
		return target;
	}

	public void setTarget(ITarget target) {
		this.target = target;
	}

}
