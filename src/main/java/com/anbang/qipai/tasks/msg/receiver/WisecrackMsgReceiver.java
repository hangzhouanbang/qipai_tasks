package com.anbang.qipai.tasks.msg.receiver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.msg.channel.sink.WisecrackSink;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.google.gson.Gson;

@EnableBinding(WisecrackSink.class)
public class WisecrackMsgReceiver {
	@Autowired
	private TaskService taskService;

	private Gson gson = new Gson();

	@StreamListener(WisecrackSink.WISECRACK)
	public void wisecrack(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("wisecrack".equals(msg)) {
			String memberId = (String) map.get("memberId");
			if (memberId != null) {
				taskService.updateTask(memberId, "俏皮话", 1);
			}
		}
	}
}
