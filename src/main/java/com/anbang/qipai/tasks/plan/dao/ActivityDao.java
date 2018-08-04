package com.anbang.qipai.tasks.plan.dao;

import java.util.List;

import com.anbang.qipai.tasks.plan.bean.Activity;

public interface ActivityDao {
	void addActivity(Activity activity);

	boolean updateActivityStateById(String activityId, String state);

	List<Activity> findActivity();

	Activity findActivityById(String activityId);
}
