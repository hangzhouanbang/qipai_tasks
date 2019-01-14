package com.anbang.qipai.tasks.plan.bean.target;

import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskAction;
import com.anbang.qipai.tasks.plan.bean.TaskMenu;
import com.anbang.qipai.tasks.plan.bean.TaskState;

/**
 * 分享朋友圈
 * 
 * @author lsc
 *
 */
public class ShareFirendsCircleTarget implements ITarget {

	private int targetFirendsCircleNum;

	private int finishFirendsCircleNum;

	@Override
	public void init(Task task, MemberDbo member) {
		this.targetFirendsCircleNum = task.getTargetNum();
		this.finishFirendsCircleNum = 0;
		TaskMenu menu = new TaskMenu();
		menu.setName("去分享");
		menu.setAction(TaskAction.SHAREGAME);
		task.setMenu(menu);
		task.setTaskState(TaskState.DOINGTASK);
	}

	@Override
	public void updateTask(Task task, MemberDbo member, int finishNum) {
		if (!TaskState.COMPLETETASK.equals(task.getTaskState()) && !TaskState.FINISHTASK.equals(task.getTaskState())) {
			finishFirendsCircleNum += finishNum;
			task.setFinishNum(finishFirendsCircleNum);
			if (finishFirendsCircleNum >= targetFirendsCircleNum) {
				task.setTaskState(TaskState.COMPLETETASK);
				task.getMenu().setName("领取");
				task.getMenu().setAction(TaskAction.REWARD);
			}
		}
	}

	@Override
	public void reset(Task task, MemberDbo member) {
		this.finishFirendsCircleNum = 0;
		task.setFinishNum(finishFirendsCircleNum);
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

	public int getTargetFirendsCircleNum() {
		return targetFirendsCircleNum;
	}

	public void setTargetFirendsCircleNum(int targetFirendsCircleNum) {
		this.targetFirendsCircleNum = targetFirendsCircleNum;
	}

	public int getFinishFirendsCircleNum() {
		return finishFirendsCircleNum;
	}

	public void setFinishFirendsCircleNum(int finishFirendsCircleNum) {
		this.finishFirendsCircleNum = finishFirendsCircleNum;
	}

}
