package com.anbang.qipai.tasks.msg.receiver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;


import com.anbang.qipai.tasks.msg.channel.HistoricaRecordSink;
import com.anbang.qipai.tasks.plan.domain.historicarecord.DianPaoHistoricalRecord;
import com.anbang.qipai.tasks.plan.domain.historicarecord.MemberHistoricalRecord;
import com.anbang.qipai.tasks.plan.domain.historicarecord.RuianHistoricalRecord;
import com.anbang.qipai.tasks.plan.domain.historicarecord.WenZhouHistoricalRecord;
import com.google.gson.Gson;

@EnableBinding(HistoricaRecordSink.class)
public class HistoricaRecordMsgReceiver {
	
	private Gson gson = new Gson();
	
	//@Autowired
	//private TaskService taskService;
	
	@StreamListener(HistoricaRecordSink.HISTORICARECORD)
	public void historicaRecord(Object payload) {
		Map<String,Integer> taskMap = new HashMap<String,Integer>();
		taskMap.put("finishInviteNum",1);
		Map map = gson.fromJson(payload.toString(),Map.class);
		Map mapData = (Map) map.get("data");
		MemberHistoricalRecord memberHistoricalRecord = gson.fromJson(gson.toJson(mapData),MemberHistoricalRecord.class);
		if(memberHistoricalRecord.getRuian() != null) {
			for(RuianHistoricalRecord ruiAn:memberHistoricalRecord.getRuian()) {
			//	taskService.updateTask(ruiAn.getMemberId(),taskMap);
			}
		}
		if(memberHistoricalRecord.getDianpao() != null) {
			for(DianPaoHistoricalRecord dianPao:memberHistoricalRecord.getDianpao()) {
				//taskService.updateTask(dianPao.getMemberId(),taskMap);
			}
		}
		if(memberHistoricalRecord.getWenzhou() != null) {
			for(WenZhouHistoricalRecord wenZhou:memberHistoricalRecord.getWenzhou()) {
			//	taskService.updateTask(wenZhou.getMemberId(),taskMap);
			}
		}
	}

}
