package com.anbang.qipai.tasks.config;

import com.anbang.qipai.tasks.plan.domain.ITarget;
import com.anbang.qipai.tasks.plan.domain.target.InviteFriendsTarget;
import com.anbang.qipai.tasks.plan.domain.target.WinGamesTarget;

public enum TargetType {
	// 0,邀请玩家
	InviteFriends(new InviteFriendsTarget()),
	// 1,赢得游戏
	WinGame(new WinGamesTarget());

	private ITarget target;

	private TargetType(ITarget target) {
		this.target = target;
	}

	public ITarget getTarget() {
		return target;
	}

	// 获得任务目的的具体实体对象 在构造任务实体对象的时候调用
	public static ITarget getITargetById(int targetId) {
		TargetType[] values = TargetType.values();
		TargetType targetType = values[targetId];
		return targetType.getTarget();
	}

}