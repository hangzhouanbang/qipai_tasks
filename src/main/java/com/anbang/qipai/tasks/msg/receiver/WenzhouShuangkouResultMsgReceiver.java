package com.anbang.qipai.tasks.msg.receiver;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.msg.channel.sink.WenzhouShuangkouResultSink;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.google.gson.Gson;

@EnableBinding(WenzhouShuangkouResultSink.class)
public class WenzhouShuangkouResultMsgReceiver {
	@Autowired
	private TaskService taskService;

	private Gson gson = new Gson();

	@StreamListener(WenzhouShuangkouResultSink.WENZHOUSHUANGKOURESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("wenzhoushuangkou ju result".equals(msg)) {
			String memberId = (String) map.get("dayingjiaId");
			if (memberId != null) {
				taskService.updateTask(memberId, "赢得游戏", 1);
				((List) map.get("playerResultList")).forEach((juPlayerResult) -> taskService
						.updateTask((String) ((Map) juPlayerResult).get("playerId"), "对局任务", 1));
			}
		}
		if ("wenzhoushuangkou pan result".equals(msg)) {
			((List) map.get("playerResultList")).forEach((panPlayerResult) -> taskService
					.updateTask((String) ((Map) panPlayerResult).get("playerId"), "完成小盘游戏", 1));
		}
	}
}
