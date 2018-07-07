package com.anbang.qipai.tasks.remote.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public CommonRemoteVO auth_trytoken(String token);

	@RequestMapping(value = "/reward/task_reward")
	public CommonRemoteVO sendReward(Integer rewardGold, Integer rewardScore, Integer rewardVip, String memberId);

}
