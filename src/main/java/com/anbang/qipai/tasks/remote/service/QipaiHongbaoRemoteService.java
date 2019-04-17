package com.anbang.qipai.tasks.remote.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anbang.qipai.tasks.remote.vo.CommonRemoteVO;

@FeignClient("qipai-hongbao")
public interface QipaiHongbaoRemoteService {

	@RequestMapping(value = "/hongbao/give_hongbao_to_member")
	public CommonRemoteVO hongbao_give_to_member(@RequestParam(value = "memberId") String memberId,
			@RequestParam(value = "amount") double amount, @RequestParam(value = "textSummary") String textSummary);
}
