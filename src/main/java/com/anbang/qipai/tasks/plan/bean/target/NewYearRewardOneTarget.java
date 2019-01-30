package com.anbang.qipai.tasks.plan.bean.target;

import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskAction;
import com.anbang.qipai.tasks.plan.bean.TaskMenu;
import com.anbang.qipai.tasks.plan.bean.TaskState;

/**
 * 新春福利1
 * 
 * @author lsc
 *
 */
public class NewYearRewardOneTarget implements ITarget {

	@Override
	public void init(Task task, MemberDbo member) {
		TaskMenu menu = new TaskMenu();
		menu.setName("领取");
		menu.setAction(TaskAction.REWARD);
		task.setMenu(menu);
		task.setTaskState(TaskState.COMPLETETASK);
		task.setRewardUrl("/task/newyear_reward");
	}

	@Override
	public void reset(Task task, MemberDbo member) {
		task.setFinishNum(0);
		task.setTaskState(TaskState.COMPLETETASK);
		task.getMenu().setName("领取");
		task.getMenu().setAction(TaskAction.REWARD);
	}

	@Override
	public void updateTask(Task task, MemberDbo member, int finishNum) {
		task.setFinishNum(task.getTargetNum());
	}

	@Override
	public void finishTask(Task task) {
		task.setTaskState(TaskState.FINISHTASK);
		task.getMenu().setName("已领取");
		task.getMenu().setAction(TaskAction.VOID);
	}

}
