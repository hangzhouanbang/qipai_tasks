package com.anbang.qipai.tasks.plan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.config.TargetType;
import com.anbang.qipai.tasks.config.TaskConfig;
import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.dao.DoingTaskDao;
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
	private DoingTaskDao doingTaskDao;

	@Autowired
	private TaskDocumentHistoryDao taskDocumentHistoryDao;

	public Map<String, List<DoingTask>> queryMemberDoingTasks(String memberId) {
		Map<String, List<DoingTask>> taskMap = new HashMap<String, List<DoingTask>>();
		addMemberDoingTask(memberId);
		List<String> typeList = TaskConfig.typeList;
		for (String type : typeList) {
			List<DoingTask> taskList = doingTaskDao.findDoingTaskByMemberIdAndType(memberId, type);
			taskMap.put(type, taskList);
		}
		return taskMap;
	}

	public void updateDoingTasks(Map<String, Object> params) {
		String[] memberIds = (String[]) params.get("memberIds");
		for (String memberId : memberIds) {
			List<DoingTask> doingTaskList = doingTaskDao.findAllDoingTaskByMemberId(memberId);
			for (DoingTask doingTask : doingTaskList) {
				Task task = doingTask.getTask();
				task.getTarget().updateTask(task, params);
			}
		}
	}

	public DoingTask getRewards(String doingTaskId) {
		return doingTaskDao.findDoingTaskById(doingTaskId);
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
					doingTask.setType(taskHistory.getType());
					doingTask.setRewardType(taskHistory.getRewardType());
					doingTask.setRewardNum(taskHistory.getRewardNum());
					Task task = new Task();
					task.setTaskState(TaskState.DOINGTASK);
					task.setTarget(TargetType.getITargetByTaskHistory(taskHistory));
					doingTask.setTask(task);
					// TODO加入进行任务
					doingTaskDao.addDoingTask(doingTask);
				}
				member.setReleaseTaskTime(taskHistory.getReleaseTime());
			}
		}
		// 更新任务游标
		memberDboDao.updateMemberDbo(member);
	}

	private boolean hasCriterion(MemberDbo member, TaskDocumentHistory task) {
		if ("true".equals(task.getVip()) || "false".equals(task.getVip())) {
			if (task.getVip().equals(member.getVip().toString())) {
				return true;
			}
			return false;
		}
		return true;
	}
}
