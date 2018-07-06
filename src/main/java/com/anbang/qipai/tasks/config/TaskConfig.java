package com.anbang.qipai.tasks.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskConfig {
	public static Map<String, TaskTemplate> templates;
	public static List<String> typeList;

	static {
		templates = new HashMap<String, TaskTemplate>();
		TaskTemplate task = new TaskTemplate();
		task.setTaskType(0);
		task.setTaskName("邀请玩家");
		templates.put(task.getTaskName(), task);
		TaskTemplate task1 = new TaskTemplate();
		task1.setTaskType(1);
		task1.setTaskName("赢得游戏");
		templates.put(task1.getTaskName(), task1);
		TaskTemplate task2 = new TaskTemplate();
		task2.setTaskType(2);
		task2.setTaskName("分享好友");
		templates.put(task2.getTaskName(), task2);
		TaskTemplate task3 = new TaskTemplate();
		task3.setTaskType(3);
		task3.setTaskName("分享朋友圈");
		templates.put(task3.getTaskName(), task3);
	}
	static {
		typeList = new ArrayList<String>();
		typeList.add("每日任务");
		typeList.add("红包任务");
		typeList.add("邀请任务");
	}
}
