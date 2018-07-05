package com.anbang.qipai.tasks.plan.domain;

public class MemberDbo {
	private String id;// 会员id
	private Boolean vip;// 是否VIP
	private Long releaseTaskTime;// 任务游标
	private Integer onlineTime;// 在线时长
	private Long lastLoginTime;// 最后登录时间
	private Long createTime;// 注册时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public Long getReleaseTaskTime() {
		return releaseTaskTime;
	}

	public void setReleaseTaskTime(Long releaseTaskTime) {
		this.releaseTaskTime = releaseTaskTime;
	}

	public Integer getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Integer onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

}
