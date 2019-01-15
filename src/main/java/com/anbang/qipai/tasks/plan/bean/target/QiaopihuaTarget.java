package com.anbang.qipai.tasks.plan.bean.target;

import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskAction;
import com.anbang.qipai.tasks.plan.bean.TaskMenu;
import com.anbang.qipai.tasks.plan.bean.TaskState;

/**
 * 俏皮话
 * 
 * @author lsc
 *
 */
public class QiaopihuaTarget implements ITarget {

	private int targetNum;
	private int finishNum;

	@Override
	public void init(Task task, MemberDbo member) {
		this.targetNum = task.getTargetNum();
		this.finishNum = 0;
		TaskMenu menu = new TaskMenu();
		menu.setName("去完成");
		menu.setAction(TaskAction.CREATEROOM);
		task.setMenu(menu);
		task.setTaskState(TaskState.DOINGTASK);
		task.setRewardUrl("/task/qiaopihua_reward");
	}

	@Override
	public void reset(Task task, MemberDbo member) {
		this.finishNum = 0;
		task.setFinishNum(finishNum);
		task.setTaskState(TaskState.DOINGTASK);
		task.getMenu().setName("去完成");
		task.getMenu().setAction(TaskAction.CREATEROOM);
	}

	@Override
	public void updateTask(Task task, MemberDbo member, int finishNum) {
		if (!TaskState.COMPLETETASK.equals(task.getTaskState()) && !TaskState.FINISHTASK.equals(task.getTaskState())) {
			finishNum += finishNum;
			task.setFinishNum(finishNum);
			if (finishNum >= targetNum) {
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

}
