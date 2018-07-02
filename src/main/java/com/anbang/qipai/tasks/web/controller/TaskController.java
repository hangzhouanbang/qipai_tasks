package com.anbang.qipai.tasks.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.tasks.msg.service.TaskDocumentHistoriesMsgService;
import com.anbang.qipai.tasks.msg.service.TaskDocumentsMsgService;
import com.anbang.qipai.tasks.plan.domain.TaskDocument;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.service.TaskDocumentHistoryService;
import com.anbang.qipai.tasks.plan.service.TaskDocumentService;
import com.anbang.qipai.tasks.web.vo.CommonVO;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskDocumentService taskDocumentService;

	@Autowired
	private TaskDocumentsMsgService taskDocumentsMsgService;

	@Autowired
	private TaskDocumentHistoryService taskDocumentHistoryService;

	@Autowired
	private TaskDocumentHistoriesMsgService taskDocumentHistoriesMsgService;

	@RequestMapping("/addtaskdocument")
	public CommonVO addTaskDocument(@RequestBody TaskDocument taskDoc) {
		CommonVO vo = new CommonVO();
		taskDocumentService.addTaskDocument(taskDoc);
		taskDocumentsMsgService.addTaskDocument(taskDoc);
		vo.setSuccess(true);
		vo.setMsg("addTaskDocument");
		return vo;
	}

	@RequestMapping("/deletetaskdocuments")
	public CommonVO deleteTaskDocuments(@RequestBody String[] taskIds) {
		CommonVO vo = new CommonVO();
		taskDocumentService.deleteTaskDocuments(taskIds);
		taskDocumentsMsgService.deleteTaskDocuments(taskIds);
		vo.setSuccess(true);
		vo.setMsg("deleteTaskDocuments");
		return vo;
	}

	@RequestMapping("/updatetaskdocument")
	public CommonVO updateTaskDocument(@RequestBody TaskDocument taskDoc) {
		CommonVO vo = new CommonVO();
		taskDocumentService.updateTaskDocument(taskDoc);
		taskDocumentsMsgService.updateTaskDocument(taskDoc);
		vo.setSuccess(true);
		vo.setMsg("updateTaskDocument");
		return vo;
	}

	@RequestMapping("/release")
	public CommonVO releaseTaskDocument(String taskId, String admin) {
		CommonVO vo = new CommonVO();
		TaskDocumentHistory taskDocumentHistory = taskDocumentService.release(taskId, admin);
		taskDocumentHistoriesMsgService.addTaskDocumentHistory(taskDocumentHistory);
		vo.setSuccess(true);
		vo.setMsg("updateTaskDocument");
		return vo;
	}
}
