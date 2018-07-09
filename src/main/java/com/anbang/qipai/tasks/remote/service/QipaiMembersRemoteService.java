package com.anbang.qipai.tasks.remote.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anbang.qipai.tasks.remote.vo.CommonRemoteVO;

/**
 * 会员中心远程服务
 * 
 * @author neo
 *
 */
@FeignClient("qipai-members")
public interface QipaiMembersRemoteService {

	@RequestMapping(value = "/auth/trytoken")
	public CommonRemoteVO auth_trytoken(@RequestParam(value = "token") String token);

	@RequestMapping(value = "/reward/task_reward")
	public CommonRemoteVO sendReward(@RequestParam(value = "rewardGold") Integer rewardGold,
			@RequestParam(value = "rewardScore") Integer rewardScore,
			@RequestParam(value = "rewardVip") Integer rewardVip, @RequestParam(value = "memberId") String memberId);

}
