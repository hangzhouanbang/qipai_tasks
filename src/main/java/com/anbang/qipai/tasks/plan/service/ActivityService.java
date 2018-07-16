package com.anbang.qipai.tasks.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.plan.dao.ActivityDao;
import com.anbang.qipai.tasks.plan.domain.Activity;

@Service
public class ActivityService {

	@Autowired
	private ActivityDao activityDao;

	public void addActivity(Activity activity) {
		activityDao.addActivity(activity);
	}

	public Activity startActivity(String activityId) {
		activityDao.updateActivityStateById(activityId, 1);
		return activityDao.findActivityById(activityId);
	}

	public Activity stopActivity(String activityId) {
		activityDao.updateActivityStateById(activityId, 0);
		return activityDao.findActivityById(activityId);
	}

	public List<Activity> findActivity() {
		return activityDao.findActivity();
	}
}
