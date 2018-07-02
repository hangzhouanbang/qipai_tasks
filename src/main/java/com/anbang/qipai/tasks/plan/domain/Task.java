package com.anbang.qipai.tasks.plan.domain;

public class Task {

	private String id;
	private int taskState;// 任务状态
	private int progress;// 进度
	private ITarget target;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTaskState() {
		return taskState;
	}

	public void setTaskState(int taskState) {
		this.taskState = taskState;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public ITarget getTarget() {
		return target;
	}

	public void setTarget(ITarget target) {
		this.target = target;
	}

}
