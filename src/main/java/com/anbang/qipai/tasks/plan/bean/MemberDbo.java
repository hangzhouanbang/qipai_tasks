package com.anbang.qipai.tasks.plan.bean;

public class MemberDbo {
	private String id;// 会员id
	private String nickname;// 会员昵称
	private String headimgurl;// 会员头像
	private boolean vip;// 是否VIP
	private int onlineTime;// 在线时长
	private long lastLoginTime;// 最后登录时间
	private long createTime;// 注册时间

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

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public int getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
