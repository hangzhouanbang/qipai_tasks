package com.anbang.qipai.tasks.plan.bean;

public class Task {
	private String id;
	private String taskId;// 任务历史id
	private String memberId;// 会员id
	private String name;// 任务主题
	private String desc;// 任务描述
	private String type;// 任务类型
	private int rewardGold;// 奖励金币数量
	private int rewardScore;// 奖励积分数量
	private int rewardVip;// 奖励会员时间
	private String taskState;// 任务状态
	private int targetNum;// 总进度
	private int finishNum;// 完成进度
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRewardGold() {
		return rewardGold;
	}

	public void setRewardGold(int rewardGold) {
		this.rewardGold = rewardGold;
	}

	public int getRewardScore() {
		return rewardScore;
	}

	public void setRewardScore(int rewardScore) {
		this.rewardScore = rewardScore;
	}

	public int getRewardVip() {
		return rewardVip;
	}

	public void setRewardVip(int rewardVip) {
		this.rewardVip = rewardVip;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public int getTargetNum() {
		return targetNum;
	}

	public void setTargetNum(int targetNum) {
		this.targetNum = targetNum;
	}

	public int getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(int finishNum) {
		this.finishNum = finishNum;
	}

	public ITarget getTarget() {
		return target;
	}

	public void setTarget(ITarget target) {
		this.target = target;
	}

}
