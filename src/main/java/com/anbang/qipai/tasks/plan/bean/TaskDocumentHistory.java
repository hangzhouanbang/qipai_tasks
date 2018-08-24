package com.anbang.qipai.tasks.plan.bean;

public class TaskDocumentHistory {
	private String id;
	private String taskDocId;
	private String name;
	private String desc;
	private String type;// 任务类型，前台
	private String taskName;// 任务种类,后台
	private Integer rewardGold;// 奖励金币数量
	private Integer rewardScore;// 奖励积分数量
	private Integer rewardVip;// 奖励会员时间
	private String vip;
	private Integer targetNum;// 完成次数
	private String state;// 状态:0,未发布,1,已发布
	private Long releaseTime;
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

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public Integer getTargetNum() {
		return targetNum;
	}

	public void setTargetNum(Integer targetNum) {
		this.targetNum = targetNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Long releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getPromulgator() {
		return promulgator;
	}

	public void setPromulgator(String promulgator) {
		this.promulgator = promulgator;
	}

}
