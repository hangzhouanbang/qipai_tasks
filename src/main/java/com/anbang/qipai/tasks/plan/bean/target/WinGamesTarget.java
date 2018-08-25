package com.anbang.qipai.tasks.plan.bean.target;

import java.util.Map;

import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;

public class WinGamesTarget implements ITarget {

	private int targetWinNum;
	private int finishWinNum;

	@Override
	public void updateTask(Task task, Map<String, Object> params) {
		if (params.get("finishWinNum") != null && !TaskState.COMPLETETASK.equals(task.getTaskState())
				&& !TaskState.FINISHTASK.equals(task.getTaskState())) {
			finishWinNum += (int) params.get("finishWinNum");
			task.setFinishNum(finishWinNum);
			if (finishWinNum >= targetWinNum) {
				task.setTaskState(TaskState.COMPLETETASK);
			}
		}
	}

	@Override
	public void reset(Task task) {
		this.finishWinNum = 0;
		task.setFinishNum(finishWinNum);
		task.setTaskState(TaskState.DOINGTASK);
	}

	@Override
	public void init(TaskDocumentHistory task) {
		this.targetWinNum = 0;
		this.targetWinNum = task.getTargetNum();
		this.finishWinNum = 0;
	}

	public int getTargetWinNum() {
		return targetWinNum;
	}

	public void setTargetWinNum(int targetWinNum) {
		this.targetWinNum = targetWinNum;
	}

	public int getFinishWinNum() {
		return finishWinNum;
	}

	public void setFinishWinNum(int finishWinNum) {
		this.finishWinNum = finishWinNum;
	}

}