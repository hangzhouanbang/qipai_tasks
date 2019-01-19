package com.anbang.qipai.tasks.plan.bean;

public class TaskDocumentHistory {
	private String id;
	private String taskDocId;
	private String name;// 任务名称
	private String desc;// 任务描述
	private String type;// 任务类型，前台分类
	private String taskName;// 任务种类,后台
	private RewardType rewardType;// 奖励类型
	private double rewardNum;// 奖励数量
	private boolean vip;// 是否vip专属任务
	private int targetNum;// 完成次数
	private long limitTime;// 限时
	private int weight;// 权重
	private TaskType taskType;// 任务分类：每日，单次
	private TaskDocumentHistoryState state;// 状态:发布，停用
	private long releaseTime;// 发布时间
	private String promulgator;// 发布者

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskDocId() {
		return taskDocId;
	}

	public void setTaskDocId(String taskDocId) {
		this.taskDocId = taskDocId;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public RewardType getRewardType() {
		return rewardType;
	}

	public void setRewardType(RewardType rewardType) {
		this.rewardType = rewardType;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public double getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(double rewardNum) {
		this.rewardNum = rewardNum;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public int getTargetNum() {
		return targetNum;
	}

	public void setTargetNum(int targetNum) {
		this.targetNum = targetNum;
	}

	public long getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(long limitTime) {
		this.limitTime = limitTime;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public TaskDocumentHistoryState getState() {
		return state;
	}

	public void setState(TaskDocumentHistoryState state) {
		this.state = state;
	}

	public long getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(long releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getPromulgator() {
		return promulgator;
	}

	public void setPromulgator(String promulgator) {
		this.promulgator = promulgator;
	}

}
