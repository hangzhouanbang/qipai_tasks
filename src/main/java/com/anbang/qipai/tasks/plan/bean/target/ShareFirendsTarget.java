package com.anbang.qipai.tasks.plan.bean.target;

import java.util.Map;

import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;

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
		if (params.get("finishFirendsNum") != null && !TaskState.COMPLETETASK.equals(task.getTaskState())
				&& !TaskState.FINISHTASK.equals(task.getTaskState())) {
			finishFirendsNum += Integer.valueOf((String) params.get("finishFirendsNum"));
			task.setFinishNum(finishFirendsNum);
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
	public void reset(Task task) {
		this.finishFirendsNum = 0;
		task.setFinishNum(finishFirendsNum);
		task.setTaskState(TaskState.DOINGTASK);
	}

}
