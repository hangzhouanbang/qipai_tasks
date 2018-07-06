package com.anbang.qipai.tasks.plan.domain.target;

import java.util.Map;

import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.domain.ITarget;
import com.anbang.qipai.tasks.plan.domain.Task;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

public class InviteFriendsTarget implements ITarget {

	private int targetInviteNum;
	private int finishInviteNum;

	@Override
	public void updateTask(Task task, Map<String, Object> params) {
		if (params.get("finishInviteNum") != null) {
			finishInviteNum += (int) params.get("finishInviteNum");
			if (finishInviteNum >= targetInviteNum) {
				task.setTaskState(TaskState.COMPLETETASK);
			}
		}
	}

	@Override
	public void init(TaskDocumentHistory task) {
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

}
