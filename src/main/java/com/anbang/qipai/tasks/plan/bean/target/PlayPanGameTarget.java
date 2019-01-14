package com.anbang.qipai.tasks.plan.bean.target;

import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskAction;
import com.anbang.qipai.tasks.plan.bean.TaskMenu;
import com.anbang.qipai.tasks.plan.bean.TaskState;

/**
 * 完成小盘游戏
 * 
 * @author lsc
 *
 */
public class PlayPanGameTarget implements ITarget {

	private int targetPanNum;
	private int finishPanNum;

	@Override
	public void init(Task task, MemberDbo member) {
		this.targetPanNum = task.getTargetNum();
		this.finishPanNum = 0;
		TaskMenu menu = new TaskMenu();
		menu.setName("去完成");
		menu.setAction(TaskAction.CREATEROOM);
		task.setMenu(menu);
		task.setTaskState(TaskState.DOINGTASK);
	}

	@Override
	public void reset(Task task, MemberDbo member) {
		this.finishPanNum = 0;
		task.setFinishNum(finishPanNum);
		task.setTaskState(TaskState.DOINGTASK);
		task.getMenu().setName("去完成");
		task.getMenu().setAction(TaskAction.CREATEROOM);
	}

	@Override
	public void updateTask(Task task, MemberDbo member, int finishNum) {
		if (!TaskState.COMPLETETASK.equals(task.getTaskState()) && !TaskState.FINISHTASK.equals(task.getTaskState())) {
			finishPanNum += finishNum;
			task.setFinishNum(finishPanNum);
			if (finishPanNum >= targetPanNum) {
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

	public int getTargetPanNum() {
		return targetPanNum;
	}

	public void setTargetPanNum(int targetPanNum) {
		this.targetPanNum = targetPanNum;
	}

	public int getFinishPanNum() {
		return finishPanNum;
	}

	public void setFinishPanNum(int finishPanNum) {
		this.finishPanNum = finishPanNum;
	}

}
