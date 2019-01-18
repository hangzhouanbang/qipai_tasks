package com.anbang.qipai.tasks.msg.receiver;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.msg.channel.sink.DianpaoMajiangResultSink;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.google.gson.Gson;

@EnableBinding(DianpaoMajiangResultSink.class)
public class DianpaoMajiangResultMsgReceiver {
	@Autowired
	private TaskService taskService;

	private Gson gson = new Gson();

	@StreamListener(DianpaoMajiangResultSink.DIANPAOMAJIANGRESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("dianpaomajiang ju result".equals(msg)) {
			Object mid = map.get("dayingjiaId");
			if (mid != null) {
				String memberId = (String) mid;
				if (memberId != null) {
					taskService.updateTask(memberId, "赢得游戏", 1);
				}
				Object playerList = map.get("playerResultList");
				if (playerList != null) {
					((List) playerList).forEach((juPlayerResult) -> {
						taskService.updateTask((String) ((Map) juPlayerResult).get("playerId"), "对局任务", 1);
						if ((Double) ((Map) juPlayerResult).get("totalScore") > 0) {
							taskService.updateTask((String) ((Map) juPlayerResult).get("playerId"), "大局正分", 1);
						}
					});
				}
			}
		}
		if ("dianpaomajiang pan result".equals(msg)) {
			Object playerList = map.get("playerResultList");
			if (playerList != null) {
				((List) playerList).forEach((panPlayerResult) -> taskService
						.updateTask((String) ((Map) panPlayerResult).get("playerId"), "完成小盘游戏", 1));
			}
		}
	}
}
