package com.anbang.qipai.tasks.plan.bean.target;

import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskAction;
import com.anbang.qipai.tasks.plan.bean.TaskMenu;
import com.anbang.qipai.tasks.plan.bean.TaskState;

/**
 * 成为会员
 * 
 * @author lsc
 *
 */
public class BecomeVIPTarget implements ITarget {

	private boolean isVIP;

	@Override
	public void init(Task task, MemberDbo member) {
		this.isVIP = false;
		TaskMenu menu = new TaskMenu();
		menu.setName("成为会员");
		menu.setAction(TaskAction.VIPSHOP);
		task.setMenu(menu);
		task.setTaskState(TaskState.DOINGTASK);
		task.setRewardUrl("/task/vip_reward");
	}

	@Override
	public void reset(Task task, MemberDbo member) {
		this.isVIP = false;
		task.setFinishNum(0);
		task.setTaskState(TaskState.DOINGTASK);
		task.getMenu().setName("成为会员");
		task.getMenu().setAction(TaskAction.VIPSHOP);
	}

	@Override
	public void updateTask(Task task, MemberDbo member, int finishNum) {
		if (!TaskState.COMPLETETASK.equals(task.getTaskState()) && !TaskState.FINISHTASK.equals(task.getTaskState())) {
			if (member.isVip()) {
				task.setFinishNum(task.getTargetNum());
				isVIP = true;
				task.setTaskState(TaskState.COMPLETETASK);
				task.getMenu().setName("领取");
				task.getMenu().setAction(TaskAction.REWARD);
			}
		}
	}

	@Override
	public void finishTask(Task task) {
		task.setTaskState(TaskState.FINISHTASK);
		task.getMenu().setName("已完成");
		task.getMenu().setAction(TaskAction.VOID);
	}

	public boolean isVIP() {
		return isVIP;
	}

	public void setVIP(boolean isVIP) {
		this.isVIP = isVIP;
	}

}
