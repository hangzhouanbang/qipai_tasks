package com.anbang.qipai.tasks.plan.domain.target;

import com.anbang.qipai.tasks.plan.domain.ITarget;
import com.anbang.qipai.tasks.plan.domain.Task;

public class WinGamesTarget implements ITarget {
	private String taskId;
	private int targetWinNum;
	private int finishWinNum;

	@Override
	public boolean checkTaskComplete(Task task) {
		return finishWinNum >= targetWinNum;
	}

}
