package com.anbang.qipai.tasks.plan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.config.TargetType;
import com.anbang.qipai.tasks.config.TaskConfig;
import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.dao.MemberDboDao;
import com.anbang.qipai.tasks.plan.dao.TaskDocumentHistoryDao;
import com.anbang.qipai.tasks.plan.domain.DoingTask;
import com.anbang.qipai.tasks.plan.domain.MemberDbo;
import com.anbang.qipai.tasks.plan.domain.Task;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

@Service
public class TaskService {

	@Autowired
	private MemberDboDao memberDboDao;

	@Autowired
	private TaskDocumentHistoryDao taskDocumentHistoryDao;

	public Map<String, List<DoingTask>> queryMemberTasks(String memberId) {
		Map<String, List<DoingTask>> taskMap = new HashMap<String, List<DoingTask>>();
		addMemberDoingTask(memberId);
		return taskMap;
	}

	public void updateTask(String memberId, Map<String, Integer> params) {

	}

	private void addMemberDoingTask(String memberId) {
		MemberDbo member = memberDboDao.findMemberById(memberId);
		long releaseTime = 0;
		if (member.getReleaseTaskTime() != null) {
			releaseTime = member.getReleaseTaskTime();
		}
		long amount = taskDocumentHistoryDao.getAmount(releaseTime);
		int size = 300;
		long pageNum = amount % size > 0 ? amount / size + 1 : amount / size;
		for (int page = 1; page <= pageNum; page++) {
			List<TaskDocumentHistory> taskList = taskDocumentHistoryDao.findTaskByReleaseTime(page, size, releaseTime);
			for (TaskDocumentHistory taskHistory : taskList) {
				if (hasCriterion(member, taskHistory)) {
					DoingTask doingTask = new DoingTask();
					doingTask.setMemberId(member.getId());
					Task task = new Task();
					task.setTaskState(TaskState.DOINGTASK);
					task.setProgress(0);
					task.setTarget(TargetType
							.getITargetById(TaskConfig.templates.get(taskHistory.getTaskName()).getTaskType()));
					doingTask.setTask(task);
					// TODO加入进行任务

				}
			}
		}
	}

	private boolean hasCriterion(MemberDbo member, TaskDocumentHistory task) {
		if ("true".equals(task.getType()) || "false".equals(task.getType())) {
			if ("true".equals(member.getVip().toString()) || "false".equals(member.getVip().toString())) {
				return true;
			}
			return false;
		}
		return true;
	}
}
