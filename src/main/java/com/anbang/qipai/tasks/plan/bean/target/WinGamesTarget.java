package com.anbang.qipai.tasks.plan.bean.target;

import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskAction;
import com.anbang.qipai.tasks.plan.bean.TaskMenu;
import com.anbang.qipai.tasks.plan.bean.TaskState;

/**
 * 赢得游戏
 * 
 * @author lsc
 *
 */
public class WinGamesTarget implements ITarget {

	private int targetWinNum;
	private int finishWinNum;

	@Override
	public void init(Task task, MemberDbo member) {
		this.targetWinNum = task.getTargetNum();
		this.finishWinNum = 0;
		TaskMenu menu = new TaskMenu();
		menu.setName("去完成");
		menu.setAction(TaskAction.CREATEROOM);
		task.setMenu(menu);
		task.setTaskState(TaskState.DOINGTASK);
		task.setRewardUrl("/task/wingame_reward");
	}

	@Override
	public void updateTask(Task task, MemberDbo member, int finishNum) {
		if (!TaskState.COMPLETETASK.equals(task.getTaskState()) && !TaskState.FINISHTASK.equals(task.getTaskState())) {
			finishWinNum += finishNum;
			task.setFinishNum(finishWinNum);
			if (finishWinNum >= targetWinNum) {
				task.setTaskState(TaskState.COMPLETETASK);
				task.getMenu().setName("领取");
				task.getMenu().setAction(TaskAction.REWARD);
			}
		}
	}

	@Override
	public void reset(Task task, MemberDbo member) {
		this.finishWinNum = 0;
		task.setFinishNum(finishWinNum);
		task.setTaskState(TaskState.DOINGTASK);
		task.getMenu().setName("去完成");
		task.getMenu().setAction(TaskAction.CREATEROOM);
	}

	@Override
	public void finishTask(Task task) {
		task.setTaskState(TaskState.FINISHTASK);
		task.getMenu().setName("已完成");
		task.getMenu().setAction(TaskAction.VOID);
	}

	public int getTargetWinNum() {
		return targetWinNum;
	}

	public void setTargetWinNum(int targetWinNum) {
		this.targetWinNum = targetWinNum;
	}

	public int getFinishWinNum() {
		return finishWinNum;
	}

	public void setFinishWinNum(int finishWinNum) {
		this.finishWinNum = finishWinNum;
	}

}
