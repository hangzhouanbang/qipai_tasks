package com.anbang.qipai.tasks.plan.bean.target;

import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskAction;
import com.anbang.qipai.tasks.plan.bean.TaskMenu;
import com.anbang.qipai.tasks.plan.bean.TaskState;

/**
 * 邀请新玩家
 * 
 * @author lsc
 *
 */
public class InviteNewMemberTarget implements ITarget {

	private int targetInviteNum;
	private int finishInviteNum;

	@Override
	public void init(Task task, MemberDbo member) {
		this.targetInviteNum = task.getTargetNum();
		this.finishInviteNum = 0;
		TaskMenu menu = new TaskMenu();
		menu.setName("去分享");
		menu.setAction(TaskAction.SHAREGAME);
		task.setMenu(menu);
		task.setTaskState(TaskState.DOINGTASK);
		task.setRewardUrl("/task/invite_newmember_reward");
	}

	@Override
	public void updateTask(Task task, MemberDbo member, int finishNum) {
		if (!TaskState.COMPLETETASK.equals(task.getTaskState()) && !TaskState.FINISHTASK.equals(task.getTaskState())) {
			finishInviteNum += finishNum;
			task.setFinishNum(finishInviteNum);
			if (finishInviteNum >= targetInviteNum) {
				task.setTaskState(TaskState.COMPLETETASK);
				task.getMenu().setName("领取");
				task.getMenu().setAction(TaskAction.REWARD);
			}
		}
	}

	@Override
	public void reset(Task task, MemberDbo member) {
		this.targetInviteNum = task.getTargetNum();
		this.finishInviteNum = 0;
		task.setFinishNum(finishInviteNum);
		task.setTaskState(TaskState.DOINGTASK);
		task.getMenu().setName("去分享");
		task.getMenu().setAction(TaskAction.SHAREGAME);
	}

	@Override
	public void finishTask(Task task) {
		task.setTaskState(TaskState.FINISHTASK);
		task.getMenu().setName("已完成");
		task.getMenu().setAction(TaskAction.VOID);
	}

	public int getTargetInviteNum() {
		return targetInviteNum;
	}

	public void setTargetInviteNum(int targetInviteNum) {
		this.targetInviteNum = targetInviteNum;
	}

	public int getFinishInviteNum() {
		return finishInviteNum;
	}

	public void setFinishInviteNum(int finishInviteNum) {
		this.finishInviteNum = finishInviteNum;
	}

}
