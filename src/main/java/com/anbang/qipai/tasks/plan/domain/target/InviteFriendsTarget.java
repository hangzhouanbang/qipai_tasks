package com.anbang.qipai.tasks.plan.domain.target;

import com.anbang.qipai.tasks.plan.domain.ITarget;
import com.anbang.qipai.tasks.plan.domain.Task;

public class InviteFriendsTarget implements ITarget {
	private String taskId;
	private int targetInviteNum;
	private int finishInviteNum;

	@Override
	public boolean checkTaskComplete(Task task) {
		return finishInviteNum >= targetInviteNum;
	}

}
