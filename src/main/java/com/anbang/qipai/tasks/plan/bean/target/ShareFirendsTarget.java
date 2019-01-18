package com.anbang.qipai.tasks.plan.bean.target;

import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskAction;
import com.anbang.qipai.tasks.plan.bean.TaskMenu;
import com.anbang.qipai.tasks.plan.bean.TaskState;

/**
 * 分享好友
 * 
 * @author lsc
 *
 */
public class ShareFirendsTarget implements ITarget {

	private int targetFirendsNum;

	private int finishFirendsNum;

	@Override
	public void init(Task task, MemberDbo member) {
		this.targetFirendsNum = task.getTargetNum();
		this.finishFirendsNum = 0;
		TaskMenu menu = new TaskMenu();
		menu.setName("去分享");
		menu.setAction(TaskAction.SHAREGAME);
		task.setMenu(menu);
		task.setTaskState(TaskState.DOINGTASK);
		task.setRewardUrl("/task/share_firends_reward");
	}

	@Override
	public void updateTask(Task task, MemberDbo member, int finishNum) {
		if (!TaskState.COMPLETETASK.equals(task.getTaskState()) && !TaskState.FINISHTASK.equals(task.getTaskState())) {
			finishFirendsNum += finishNum;
			task.setFinishNum(finishFirendsNum);
			if (finishFirendsNum >= targetFirendsNum) {
				task.setTaskState(TaskState.COMPLETETASK);
				task.getMenu().setName("领取");
				task.getMenu().setAction(TaskAction.REWARD);
			}
		}
	}

	@Override
	public void reset(Task task, MemberDbo member) {
		this.targetFirendsNum = task.getTargetNum();
		this.finishFirendsNum = 0;
		task.setFinishNum(finishFirendsNum);
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

	public int getTargetFirendsNum() {
		return targetFirendsNum;
	}

	public void setTargetFirendsNum(int targetFirendsNum) {
		this.targetFirendsNum = targetFirendsNum;
	}

	public int getFinishFirendsNum() {
		return finishFirendsNum;
	}

	public void setFinishFirendsNum(int finishFirendsNum) {
		this.finishFirendsNum = finishFirendsNum;
	}

}
