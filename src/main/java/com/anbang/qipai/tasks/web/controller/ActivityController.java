package com.anbang.qipai.tasks.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.tasks.plan.domain.Activity;
import com.anbang.qipai.tasks.plan.service.ActivityService;
import com.anbang.qipai.tasks.web.vo.CommonVO;

@RestController
@RequestMapping("/activity")
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@RequestMapping("/addactivity")
	public CommonVO addActivity(Activity activity) {
		CommonVO vo = new CommonVO();
		activityService.addActivity(activity);
		vo.setSuccess(true);
		vo.setMsg("add activity success");
		return vo;
	}

	@RequestMapping("/startactivity")
	public CommonVO startActivity(String activityId) {
		CommonVO vo = new CommonVO();
		Activity activity = activityService.startActivity(activityId);
		vo.setSuccess(true);
		vo.setMsg("start activity success");
		vo.setData(activity);
		return vo;
	}

	@RequestMapping("/stopactivity")
	public CommonVO stopActivity(String activityId) {
		CommonVO vo = new CommonVO();
		Activity activity = activityService.stopActivity(activityId);
		vo.setSuccess(true);
		vo.setMsg("start activity success");
		vo.setData(activity);
		return vo;
	}

	@RequestMapping("/queryactivity")
	public CommonVO queryActivity() {
		CommonVO vo = new CommonVO();
		List<Activity> activities = activityService.findActivity();
		vo.setSuccess(true);
		vo.setMsg("activities");
		vo.setData(activities);
		return vo;
	}
}
