package com.anbang.qipai.tasks.plan.domain.target;

import java.util.Map;

import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.domain.ITarget;
import com.anbang.qipai.tasks.plan.domain.Task;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

public class ShareFirendsCircleTarget implements ITarget{
	
	private int targetFirendsCircleNum;
	
	private int finishFirendsCircleNum;

	@Override
	public void init(TaskDocumentHistory task) {
		this.targetFirendsCircleNum = task.getTargetNum();
		this.finishFirendsCircleNum = 0;
	}

	@Override
	public void updateTask(Task task, Map<String, Object> params) {
		if (params.get("finishFirendsCircleNum") != null && task.getTaskState() != TaskState.FINISHTASK) {
			finishFirendsCircleNum += (int) params.get("finishFirendsCircleNum");
			if (finishFirendsCircleNum >= targetFirendsCircleNum) {
				task.setTaskState(TaskState.COMPLETETASK);
			}
		}
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
