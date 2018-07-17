package com.anbang.qipai.tasks.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.config.ActivityState;
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
		activityDao.updateActivityStateById(activityId, ActivityState.START);
		return activityDao.findActivityById(activityId);
	}

	public Activity stopActivity(String activityId) {
		activityDao.updateActivityStateById(activityId, ActivityState.STOP);
		return activityDao.findActivityById(activityId);
	}

	public List<Activity> findActivity() {
		return activityDao.findActivity();
	}
}
