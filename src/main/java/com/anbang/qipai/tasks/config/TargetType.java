package com.anbang.qipai.tasks.config;

import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.bean.target.InviteFriendsTarget;
import com.anbang.qipai.tasks.plan.bean.target.ShareFirendsCircleTarget;
import com.anbang.qipai.tasks.plan.bean.target.ShareFirendsTarget;
import com.anbang.qipai.tasks.plan.bean.target.WinGamesTarget;

public enum TargetType {
	// 0,邀请玩家
	InviteFriends(new InviteFriendsTarget()),
	// 1,赢得游戏
	WinGames(new WinGamesTarget()),
	// 2,分享好友
	ShareFirends(new ShareFirendsTarget()),
	// 3,分享朋友圈
	ShareFirendsCircle(new ShareFirendsCircleTarget());
	private ITarget target;

	private TargetType(ITarget target) {
		this.target = target;
	}

	public ITarget getTarget() {
		return target;
	}

	// 获得任务目的的具体实体对象 在构造任务实体对象的时候调用
	public static ITarget getITargetByTaskHistory(TaskDocumentHistory task) {
		TargetType[] values = TargetType.values();
		TargetType targetType = values[TaskConfig.templates.get(task.getTaskName()).getTaskType()];
		targetType.getTarget().init(task);
		return targetType.getTarget();
	}

}
