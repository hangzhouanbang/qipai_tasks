package com.anbang.qipai.tasks.plan.domain;

public class Task {
	private String id;
	private String taskId;// 任务历史id
	private String memberId;// 会员id
	private String type;// 任务类型
	private Integer rewardGold;// 奖励金币数量
	private Integer rewardScore;// 奖励积分数量
	private Integer rewardVip;// 奖励会员时间
	private String taskState;// 任务状态
	private Integer targetNum;// 总进度
	private Integer finishNum;// 完成进度
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

	public Integer getRewardGold() {
		return rewardGold;
	}

	public void setRewardGold(Integer rewardGold) {
		this.rewardGold = rewardGold;
	}

	public Integer getRewardScore() {
		return rewardScore;
	}

	public void setRewardScore(Integer rewardScore) {
		this.rewardScore = rewardScore;
	}

	public Integer getRewardVip() {
		return rewardVip;
	}

	public void setRewardVip(Integer rewardVip) {
		this.rewardVip = rewardVip;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public Integer getTargetNum() {
		return targetNum;
	}

	public void setTargetNum(Integer targetNum) {
		this.targetNum = targetNum;
	}

	public Integer getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(Integer finishNum) {
		this.finishNum = finishNum;
	}

	public ITarget getTarget() {
		return target;
	}

	public void setTarget(ITarget target) {
		this.target = target;
	}

}
