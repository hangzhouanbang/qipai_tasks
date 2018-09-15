package com.anbang.qipai.tasks.msg.receiver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.msg.channel.FangpaoMajiangResultSink;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.google.gson.Gson;

@EnableBinding(FangpaoMajiangResultSink.class)
public class FangpaoMajiangResultMsgReceiver {
	@Autowired
	private TaskService taskService;

	private Gson gson = new Gson();

	@StreamListener(FangpaoMajiangResultSink.FANGPAOMAJIANGRESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("fangpaomajiang ju result".equals(msg)) {
			Map<String, Object> params = new HashMap<>();
			String memberId = (String) map.get("dayingjiaId");
			if (memberId != null) {
				params.put("memberId", map.get("dayingjiaId"));
				params.put("finishWinNum", 1);
				taskService.updateTasks(params);
			}
		}
	}
}
