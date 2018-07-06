package com.anbang.qipai.tasks.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.tasks.config.TaskConfig;
import com.anbang.qipai.tasks.plan.domain.DoingTask;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.service.MemberAuthService;
import com.anbang.qipai.tasks.plan.service.TaskDocumentHistoryService;
import com.anbang.qipai.tasks.plan.service.TaskService;
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

	@RequestMapping("/querymemberdoingtasks")
	public CommonVO queryMemberDoingTasks(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		Map<String, List<DoingTask>> taskMap = taskService.queryMemberDoingTasks(token);
		vo.setSuccess(true);
		vo.setMsg("taskMap");
		vo.setData(taskMap);
		return vo;
	}

	@RequestMapping("/updatedoingtasks")
	public void updateDoingTasks(Map<String, Object> params) {
		taskService.updateDoingTasks(params);
	}

	@RequestMapping("/getrewards")
	public void getRewards(String token, String doingTaskId) {
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId != null) {
			DoingTask doingTask = taskService.getRewards(doingTaskId);
			// TODO根据奖励类型Kafka发送奖励并增加记录
		}
	}
}
