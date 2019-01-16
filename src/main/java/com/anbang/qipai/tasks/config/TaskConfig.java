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
		task.setTaskName("邀请新玩家");
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
		TaskTemplate task4 = new TaskTemplate();
		task4.setTaskType(4);
		task4.setTaskName("成为会员");
		templates.put(task4.getTaskName(), task4);
		TaskTemplate task5 = new TaskTemplate();
		task5.setTaskType(5);
		task5.setTaskName("红包点福利");
		templates.put(task5.getTaskName(), task5);
		TaskTemplate task6 = new TaskTemplate();
		task6.setTaskType(6);
		task6.setTaskName("完成小盘游戏");
		templates.put(task6.getTaskName(), task6);
		TaskTemplate task7 = new TaskTemplate();
		task7.setTaskType(7);
		task7.setTaskName("俏皮话");
		templates.put(task7.getTaskName(), task7);
		TaskTemplate task8 = new TaskTemplate();
		task8.setTaskType(8);
		task8.setTaskName("对局任务");
		templates.put(task8.getTaskName(), task8);
		TaskTemplate task9 = new TaskTemplate();
		task9.setTaskType(9);
		task9.setTaskName("新春福利1");
		templates.put(task9.getTaskName(), task9);
	}
	static {
		typeList = new ArrayList<String>();
		typeList.add("每日任务");
		typeList.add("邀请豪礼");
	}
}
