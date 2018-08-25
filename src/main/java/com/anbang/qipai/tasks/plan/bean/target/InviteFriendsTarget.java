package com.anbang.qipai.tasks.plan.bean.target;

import java.util.Map;

import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;

public class InviteFriendsTarget implements ITarget {

	private int targetInviteNum;
	private int finishInviteNum;

	@Override
	public void updateTask(Task task, Map<String, Object> params) {
		if (params.get("finishInviteNum") != null && !TaskState.COMPLETETASK.equals(task.getTaskState())
				&& !TaskState.FINISHTASK.equals(task.getTaskState())) {
			finishInviteNum += Integer.valueOf((String) params.get("finishInviteNum"));
			task.setFinishNum(finishInviteNum);
			if (finishInviteNum >= targetInviteNum) {
				task.setTaskState(TaskState.COMPLETETASK);
			}
		}
	}

	@Override
	public void init(TaskDocumentHistory task) {
		this.targetInviteNum = 0;
		this.targetInviteNum = task.getTargetNum();
		this.finishInviteNum = 0;
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

	@Override
	public void reset(Task task) {
		this.finishInviteNum = 0;
		task.setFinishNum(finishInviteNum);
		task.setTaskState(TaskState.DOINGTASK);
	}

}
