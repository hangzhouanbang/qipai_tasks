package com.anbang.qipai.tasks.plan.domain;

public class TaskDocumentHistory extends TaskDocument {
	private String state;// 状态
	private Long releaseTime;
	private String promulgator;

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
