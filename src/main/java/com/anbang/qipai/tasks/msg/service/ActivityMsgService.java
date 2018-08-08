package com.anbang.qipai.tasks.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.tasks.msg.channel.ActivitySoure;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.bean.Activity;

@EnableBinding(ActivitySoure.class)
public class ActivityMsgService {

	@Autowired
	private ActivitySoure activitySoure;

	public void addActivity(Activity activity) {
		CommonMO mo = new CommonMO();
		mo.setMsg("add activity");
		mo.setData(activity);

		activitySoure.activity().send(MessageBuilder.withPayload(mo).build());
	}

	public void startActivity(Activity activity) {
		CommonMO mo = new CommonMO();
		mo.setMsg("start activity");
		mo.setData(activity);

		activitySoure.activity().send(MessageBuilder.withPayload(mo).build());
	}

	public void stopActivity(Activity activity) {
		CommonMO mo = new CommonMO();
		mo.setMsg("stop activity");
		mo.setData(activity);

		activitySoure.activity().send(MessageBuilder.withPayload(mo).build());
	}
}
