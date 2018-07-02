package com.anbang.qipai.tasks.plan.domain;

import java.util.List;

public class MemberDbo {
	private String id;// 会员id
	private String nickname;// 会员昵称
	private Boolean vip;// 是否VIP
	private long releaseTaskTime;// 任务游标
	private List<Task> doingTasks;
	private List<Task> finishTasks;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public long getReleaseTaskTime() {
		return releaseTaskTime;
	}

	public void setReleaseTaskTime(long releaseTaskTime) {
		this.releaseTaskTime = releaseTaskTime;
	}

	public List<Task> getDoingTasks() {
		return doingTasks;
	}

	public void setDoingTasks(List<Task> doingTasks) {
		this.doingTasks = doingTasks;
	}

	public List<Task> getFinishTasks() {
		return finishTasks;
	}

	public void setFinishTasks(List<Task> finishTasks) {
		this.finishTasks = finishTasks;
	}

}
