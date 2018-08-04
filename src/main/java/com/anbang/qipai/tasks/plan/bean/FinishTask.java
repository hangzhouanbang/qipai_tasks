package com.anbang.qipai.tasks.plan.bean;

/**
 * 完成的任务
 **/
public class FinishTask extends Task {

	private long finishTime;// 完成时间

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

}
