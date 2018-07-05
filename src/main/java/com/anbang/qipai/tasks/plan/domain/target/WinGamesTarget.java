package com.anbang.qipai.tasks.plan.domain.target;

import java.util.Map;

import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.domain.ITarget;
import com.anbang.qipai.tasks.plan.domain.Task;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

public class WinGamesTarget implements ITarget {
	private String taskId;
	private int targetWinNum;
	private int finishWinNum;

	@Override
	public void updateTask(Task task, Map<String, Object> params) {
		if (params.get("finishWinNum") != null && task.getTaskState() != TaskState.FINISHTASK) {
			finishWinNum += (int) params.get("finishWinNum");
			if (finishWinNum >= targetWinNum) {
				task.setTaskState(TaskState.COMPLETETASK);
			}
		}
	}

	@Override
	public void init(TaskDocumentHistory task) {
		this.targetWinNum = task.getTargetNum();
		this.finishWinNum = 0;
	}

}
