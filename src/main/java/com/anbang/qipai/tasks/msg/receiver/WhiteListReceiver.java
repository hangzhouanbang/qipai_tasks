package com.anbang.qipai.tasks.msg.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.tasks.msg.channel.sink.WhiteListSink;
import com.anbang.qipai.tasks.msg.msjobj.CommonMO;
import com.anbang.qipai.tasks.plan.bean.WhiteList;
import com.anbang.qipai.tasks.plan.service.WhiteListService;
import com.google.gson.Gson;

/**
 * @author yins
 * @Description: 白名单消息消费
 */
@EnableBinding(WhiteListSink.class)
public class WhiteListReceiver {

	@Autowired
	private WhiteListService whiteListService;

	private Gson gson = new Gson();

	@StreamListener(WhiteListSink.WHITELIST)
	public void tasks(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		if ("add whitelist".equals(msg)) {
			WhiteList whiteList = gson.fromJson(json, WhiteList.class);
			whiteListService.insert(whiteList);
		}
		if ("remove whitelist".equals(msg)) {
			String[] ids = gson.fromJson(json, String[].class);
			whiteListService.remove(ids);
		}
	}
}
