package com.anbang.qipai.tasks.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.tasks.config.TaskConfig;
import com.anbang.qipai.tasks.config.TaskDocumentHistoryState;
import com.anbang.qipai.tasks.msg.service.TasksMsgService;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.service.MemberAuthService;
import com.anbang.qipai.tasks.plan.service.TaskDocumentHistoryService;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.anbang.qipai.tasks.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.tasks.remote.vo.CommonRemoteVO;
import com.anbang.qipai.tasks.web.vo.CommonVO;
import com.anbang.qipai.tasks.web.vo.TaskVO;

/**
 * 任务管理
 * 
 * @author 林少聪 2018.8.6
 *
 */
@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private TaskDocumentHistoryService taskDocumentHistoryService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private TasksMsgService tasksMsgService;

	@Autowired
	private QipaiMembersRemoteService qipaiMembersRemoteService;

	/**
	 * 查询任务类型、种类
	 * 
	 * @return
	 */
	@RequestMapping("/querytaskconfig")
	public CommonVO queryTaskConfig() {
		CommonVO vo = new CommonVO();
		vo.setSuccess(true);
		vo.setMsg("TaskConfig");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("templates", TaskConfig.templates);
		map.put("typeList", TaskConfig.typeList);
		vo.setData(map);
		return vo;
	}

	/**
	 * 查询任务类型
	 * 
	 * @return
	 */
	@RequestMapping("/querytasktype")
	public CommonVO queryTaskType() {
		CommonVO vo = new CommonVO();
		vo.setSuccess(true);
		vo.setMsg("typeList");
		vo.setData(TaskConfig.typeList);
		return vo;
	}

	/**
	 * 发布任务
	 * 
	 * @param task
	 * @return
	 */
	@RequestMapping("/release")
	public CommonVO releaseTask(@RequestBody TaskDocumentHistory task) {
		CommonVO vo = new CommonVO();
		task.setReleaseTime(System.currentTimeMillis());
		task.setState(TaskDocumentHistoryState.START);
		taskDocumentHistoryService.releaseTask(task);
		tasksMsgService.releaseTask(task);
		return vo;
	}

	/**
	 * 撤回任务
	 * 
	 * @param taskIds
	 * @return
	 */
	@RequestMapping("/withdraw")
	public CommonVO withdrawTask(String taskId) {
		CommonVO vo = new CommonVO();
		TaskDocumentHistory task = taskDocumentHistoryService.withdrawTaskDocumentHistory(taskId);
		tasksMsgService.withdrawTaskDocumentHistory(task);
		return vo;
	}

	/**
	 * 查询玩家个人任务
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping("/querymembertasks")
	public CommonVO queryMemberTasks(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		List<TaskVO> taskVos = taskService.queryMemberTasks(memberId);
		vo.setSuccess(true);
		vo.setMsg("taskList");
		vo.setData(taskVos);
		return vo;
	}

	/**
	 * 更新任务进度
	 * 
	 * @param params
	 */
	@RequestMapping("/updatetasks")
	public void updateTasks(@RequestParam Map<String, Object> params) {
		taskService.updateTasks(params);
	}

	/**
	 * 获取任务奖励
	 * 
	 * @param token
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/getrewards")
	public CommonVO getRewards(String token, String taskId) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		Task task = taskService.getRewards(taskId);
		if (task == null) {
			vo.setSuccess(false);
			vo.setMsg("not found task");
			return vo;
		}
		CommonRemoteVO commonRemoteVo = qipaiMembersRemoteService.sendReward(task.getRewardGold(),
				task.getRewardScore(), task.getRewardVip(), memberId);
		if (commonRemoteVo.isSuccess()) {
			taskService.finishTask(taskId);
		}
		vo.setSuccess(commonRemoteVo.isSuccess());
		vo.setMsg(commonRemoteVo.getMsg());
		vo.setData(task);
		return vo;
	}

	/**
	 * 重置每日任务
	 * 
	 */
	@Scheduled(cron = "0 0 0 * * ?") // 每天凌晨
	public void resetEveryDayTask() {
		taskService.reset("每日任务");
	}
}
