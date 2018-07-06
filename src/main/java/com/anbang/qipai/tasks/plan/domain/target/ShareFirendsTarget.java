package com.anbang.qipai.tasks.plan.domain.target;

import java.util.Map;

import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.domain.ITarget;
import com.anbang.qipai.tasks.plan.domain.Task;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

public class ShareFirendsTarget implements ITarget {

	private int targetFirendsNum;

	private int finishFirendsNum;

	@Override
	public void init(TaskDocumentHistory task) {
		this.targetFirendsNum = task.getTargetNum();
		this.finishFirendsNum = 0;
	}

	@Override
	public void updateTask(Task task, Map<String, Object> params) {
		if (params.get("finishFirendsNum") != null && task.getTaskState() != TaskState.FINISHTASK) {
			finishFirendsNum += (int) params.get("finishFirendsNum");
			if (finishFirendsNum >= targetFirendsNum) {
				task.setTaskState(TaskState.COMPLETETASK);
			}
		}
	}

	public int getTargetFirendsNum() {
		return targetFirendsNum;
	}

	public void setTargetFirendsNum(int targetFirendsNum) {
		this.targetFirendsNum = targetFirendsNum;
	}

	public int getFinishFirendsNum() {
		return finishFirendsNum;
	}

	public void setFinishFirendsNum(int finishFirendsNum) {
		this.finishFirendsNum = finishFirendsNum;
	}

	@Override
	public void reset() {
		this.finishFirendsNum = 0;
	}

}
