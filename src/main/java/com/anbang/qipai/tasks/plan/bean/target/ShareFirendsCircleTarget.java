package com.anbang.qipai.tasks.plan.bean.target;

import java.util.Map;

import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;

public class ShareFirendsCircleTarget implements ITarget {

	private int targetFirendsCircleNum;

	private int finishFirendsCircleNum;

	@Override
	public void init(TaskDocumentHistory task) {
		this.targetFirendsCircleNum = task.getTargetNum();
		this.finishFirendsCircleNum = 0;
	}

	@Override
	public void updateTask(Task task, Map<String, Object> params) {
		if (params.get("finishFirendsCircleNum") != null && !TaskState.COMPLETETASK.equals(task.getTaskState())
				&& !TaskState.FINISHTASK.equals(task.getTaskState())) {
			finishFirendsCircleNum += Integer.valueOf((String) params.get("finishFirendsCircleNum"));
			task.setFinishNum(finishFirendsCircleNum);
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

	@Override
	public void reset(Task task) {
		this.finishFirendsCircleNum = 0;
		task.setFinishNum(finishFirendsCircleNum);
		task.setTaskState(TaskState.DOINGTASK);
	}

}
