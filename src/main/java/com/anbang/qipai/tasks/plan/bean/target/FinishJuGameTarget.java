package com.anbang.qipai.tasks.plan.bean.target;

import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskAction;
import com.anbang.qipai.tasks.plan.bean.TaskMenu;
import com.anbang.qipai.tasks.plan.bean.TaskState;

/**
 * 对局任务
 * 
 * @author lsc
 *
 */
public class FinishJuGameTarget implements ITarget {

	private int targetJuNum;
	private int finishJuNum;

	@Override
	public void init(Task task, MemberDbo member) {
		this.targetJuNum = task.getTargetNum();
		this.finishJuNum = 0;
		TaskMenu menu = new TaskMenu();
		menu.setName("去完成");
		menu.setAction(TaskAction.CREATEROOM);
		task.setMenu(menu);
		task.setTaskState(TaskState.DOINGTASK);
		task.setRewardUrl("/task/jugame_reward");
	}

	@Override
	public void reset(Task task, MemberDbo member) {
		this.finishJuNum = 0;
		task.setFinishNum(finishJuNum);
		task.setTaskState(TaskState.DOINGTASK);
		task.getMenu().setName("去完成");
		task.getMenu().setAction(TaskAction.CREATEROOM);
	}

	@Override
	public void updateTask(Task task, MemberDbo member, int finishNum) {
		if (!TaskState.COMPLETETASK.equals(task.getTaskState()) && !TaskState.FINISHTASK.equals(task.getTaskState())) {
			finishJuNum += finishNum;
			task.setFinishNum(finishJuNum);
			if (finishJuNum >= targetJuNum) {
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

	public int getTargetJuNum() {
		return targetJuNum;
	}

	public void setTargetJuNum(int targetJuNum) {
		this.targetJuNum = targetJuNum;
	}

	public int getFinishJuNum() {
		return finishJuNum;
	}

	public void setFinishJuNum(int finishJuNum) {
		this.finishJuNum = finishJuNum;
	}

}
