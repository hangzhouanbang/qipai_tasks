package com.anbang.qipai.tasks.msg.receiver;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.msg.channel.sink.WenzhouMajiangResultSink;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.google.gson.Gson;

@EnableBinding(WenzhouMajiangResultSink.class)
public class WenzhouMajiangResultMsgReceiver {
	@Autowired
	private TaskService taskService;

	private Gson gson = new Gson();

	@StreamListener(WenzhouMajiangResultSink.WENZHOUMAJIANGRESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("wenzhoumajiang ju result".equals(msg)) {
			Object mid = map.get("dayingjiaId");
			Object lpn = map.get("lastPanNo");
			Object ps = map.get("panshu");
			if (mid != null && lpn != null && ps != null) {
				String memberId = (String) mid;
				int lastPanNo = ((Double) lpn).intValue();
				int panshu = ((Double) ps).intValue();
				if (memberId != null) {
					taskService.updateTask(memberId, "赢得游戏", 1);
				}
				Object playerList = map.get("playerResultList");
				if (playerList != null && lastPanNo == panshu) {
					((List) playerList).forEach((juPlayerResult) -> {
						taskService.updateTask((String) ((Map) juPlayerResult).get("playerId"), "对局任务", 1);
						if ((Double) ((Map) juPlayerResult).get("totalScore") > 0) {
							taskService.updateTask((String) ((Map) juPlayerResult).get("playerId"), "大局正分", 1);
						}
					});
				}
			}
		}
		if ("wenzhoumajiang pan result".equals(msg)) {
			Object playerList = map.get("playerResultList");
			if (playerList != null) {
				((List) playerList).forEach((panPlayerResult) -> taskService
						.updateTask((String) ((Map) panPlayerResult).get("playerId"), "完成小盘游戏", 1));
			}
		}
	}
}
