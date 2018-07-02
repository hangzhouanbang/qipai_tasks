package com.anbang.qipai.tasks.config;

public class TaskState {
	public static final int cannotAccept = -1;// 不可接
	public static final int canAccept = 0;// 可接
	public static final int doTasking = 1;// 进行中
	public static final int completeTask = 2;// 完成，未领奖
	public static final int finishTask = 3;// 完成，已领奖
}
