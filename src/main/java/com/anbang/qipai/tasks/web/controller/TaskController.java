package com.anbang.qipai.tasks.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.tasks.config.TaskConfig;
import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.domain.Task;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.service.MemberAuthService;
import com.anbang.qipai.tasks.plan.service.TaskDocumentHistoryService;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.anbang.qipai.tasks.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.tasks.remote.vo.CommonRemoteVO;
import com.anbang.qipai.tasks.web.vo.CommonVO;

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
	private QipaiMembersRemoteService qipaiMembersRemoteService;

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

	@RequestMapping("/querytasktype")
	public CommonVO queryTaskType() {
		CommonVO vo = new CommonVO();
		vo.setSuccess(true);
		vo.setMsg("typeList");
		vo.setData(TaskConfig.typeList);
		return vo;
	}

	@RequestMapping("/release")
	public CommonVO releaseTask(@RequestBody TaskDocumentHistory task) {
		CommonVO vo = new CommonVO();
		taskDocumentHistoryService.releaseTask(task);
		vo.setSuccess(true);
		vo.setMsg("ReleaseTask");
		vo.setData(task);
		return vo;
	}

	@RequestMapping("/withdraw")
	public CommonVO withdrawTask(@RequestBody String[] taskIds) {
		CommonVO vo = new CommonVO();
		vo.setSuccess(false);
		vo.setMsg("Tasks WithDraw Fail");
		if (taskDocumentHistoryService.withdrawTaskDocumentHistory(taskIds)) {
			vo.setSuccess(true);
			vo.setMsg("WithDraw TaskDocument");
		}
		return vo;
	}

	@RequestMapping("/querymembertasks")
	public CommonVO queryMemberTasks(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		Map<String, List<Task>> taskMap = taskService.queryMemberTasks(memberId);
		vo.setSuccess(true);
		vo.setMsg("taskMap");
		vo.setData(taskMap);
		return vo;
	}

	@RequestMapping("/updatetasks")
	public void updateTasks(@RequestParam Map<String, Object> params) {
		taskService.updateTasks(params);
	}

	@RequestMapping("/getrewards")
	public CommonRemoteVO getRewards(String token, String taskId) {
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		CommonRemoteVO vo = new CommonRemoteVO();
		if (memberId != null) {
			Task task = taskService.getRewards(taskId);
			// 调用服务添加奖励
			vo = qipaiMembersRemoteService.sendReward(task.getRewardType(), task.getRewardNum(), memberId);
			if (vo.isSuccess()) {
				task.setTaskState(TaskState.FINISHTASK);
				return vo;
			}
		}
		vo.setSuccess(false);
		return vo;
	}
}
