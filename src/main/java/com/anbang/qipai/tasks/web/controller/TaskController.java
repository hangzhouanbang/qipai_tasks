package com.anbang.qipai.tasks.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.tasks.config.TaskConfig;
import com.anbang.qipai.tasks.config.TaskTemplate;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.service.TaskDocumentHistoryService;
import com.anbang.qipai.tasks.web.vo.CommonVO;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskDocumentHistoryService taskDocumentHistoryService;

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

	@RequestMapping("/querytaskcriterions")
	public CommonVO queryTaskCriterions(String taskName) {
		CommonVO vo = new CommonVO();
		TaskTemplate template = TaskConfig.templates.get(taskName);
		if (template == null) {
			vo.setSuccess(false);
			vo.setMsg("Task Criterions Not Found");
			return vo;
		}
		vo.setSuccess(true);
		vo.setMsg("Task Criterions");
		vo.setData(template.getCriterions());
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
	public CommonVO withdrawTask(@RequestParam(value = "taskId") String taskId) {
		CommonVO vo = new CommonVO();
		vo.setSuccess(false);
		vo.setMsg("Tasks WithDraw Fail");
		if (taskDocumentHistoryService.withdrawTaskDocumentHistory(taskId)) {
			vo.setSuccess(true);
			vo.setMsg("WithDraw TaskDocument");
			vo.setData(taskDocumentHistoryService.findTaskById(taskId));
		}
		return vo;
	}

}
