package com.anbang.qipai.tasks.config;

import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.bean.target.BecomeVIPTarget;
import com.anbang.qipai.tasks.plan.bean.target.FinishJuGameTarget;
import com.anbang.qipai.tasks.plan.bean.target.HongbaodianRewardTarget;
import com.anbang.qipai.tasks.plan.bean.target.InviteNewMemberTarget;
import com.anbang.qipai.tasks.plan.bean.target.NewYearRewardOneTarget;
import com.anbang.qipai.tasks.plan.bean.target.PlayPanGameTarget;
import com.anbang.qipai.tasks.plan.bean.target.PositiveScoreOfJuTarget;
import com.anbang.qipai.tasks.plan.bean.target.QiaopihuaTarget;
import com.anbang.qipai.tasks.plan.bean.target.ShareFirendsCircleTarget;
import com.anbang.qipai.tasks.plan.bean.target.ShareFirendsTarget;
import com.anbang.qipai.tasks.plan.bean.target.WinGamesTarget;

public enum TargetType {
	// 0,邀请新玩家
	InviteNewMember(new InviteNewMemberTarget()),
	// 1,赢得游戏
	WinGames(new WinGamesTarget()),
	// 2,分享好友
	ShareFirends(new ShareFirendsTarget()),
	// 3,分享朋友圈
	ShareFirendsCircle(new ShareFirendsCircleTarget()),
	// 4,成为会员
	BecomeVIP(new BecomeVIPTarget()),
	// 5,红包点福利
	HongbaodianReward(new HongbaodianRewardTarget()),
	// 6,完成小盘游戏
	PlayPanGame(new PlayPanGameTarget()),
	// 7,俏皮话
	Qiaopihua(new QiaopihuaTarget()),
	// 8,对局任务
	DuijuGame(new FinishJuGameTarget()),
	// 9,新春福利1
	NewYearRewardOne(new NewYearRewardOneTarget()),
	// 10,大局正分
	PositiveScoreOfJuReward(new PositiveScoreOfJuTarget());

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
		return targetType.getTarget();
	}

}
