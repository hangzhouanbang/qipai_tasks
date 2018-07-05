package com.anbang.qipai.tasks.config;

public class TaskState {
	public static final int CAANOTACCEPT = -1;// 不可接
	public static final int CANACCEPT = 0;// 可接
	public static final int DOTASKING = 1;// 进行中
	public static final int COMPLETETASK = 2;// 完成，未领奖
	public static final int FINISHTASK = 3;// 完成，已领奖
}
