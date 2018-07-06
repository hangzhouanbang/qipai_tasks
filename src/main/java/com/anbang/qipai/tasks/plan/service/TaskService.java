package com.anbang.qipai.tasks.plan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.config.TargetType;
import com.anbang.qipai.tasks.config.TaskConfig;
import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.dao.MemberDboDao;
import com.anbang.qipai.tasks.plan.dao.TaskDao;
import com.anbang.qipai.tasks.plan.dao.TaskDocumentHistoryDao;
import com.anbang.qipai.tasks.plan.domain.MemberDbo;
import com.anbang.qipai.tasks.plan.domain.Task;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;
import com.anbang.qipai.tasks.web.vo.TaskVO;

@Service
public class TaskService {

	@Autowired
	private MemberDboDao memberDboDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private TaskDocumentHistoryDao taskDocumentHistoryDao;

	public List<TaskVO> queryMemberTasks(String memberId) {
		List<TaskVO> taskVos = new ArrayList<TaskVO>();
		addMemberTasks(memberId);
		List<String> typeList = TaskConfig.typeList;
		for (String type : typeList) {
			TaskVO taskVo = new TaskVO();
			taskVo.setType(type);
			List<Task> taskList = taskDao.findTaskByMemberIdAndType(memberId, type);
			taskVo.setTaskList(taskList);
			taskVos.add(taskVo);
		}
		return taskVos;
	}

	public void updateTasks(Map<String, Object> params) {
		String[] memberIds = (String[]) params.get("memberIds");
		String memberId = (String) params.get("memberId");
		if(memberIds != null) {
			for (String memberId1 : memberIds) {
				List<Task> taskList = taskDao.findTaskByMemberId(memberId1);
				for (Task task : taskList) {
					task.getTarget().updateTask(task, params);
				}
			}
		}
		if(memberId != null) {
			List<Task> taskList = taskDao.findTaskByMemberId(memberId);
			for (Task task : taskList) {
				task.getTarget().updateTask(task, params);
			}
		}
	}

	public Task getRewards(String taskId) {
		Task task = taskDao.findTaskById(taskId);
		if (TaskState.COMPLETETASK.equals(task.getTaskState())) {
			return taskDao.findTaskById(taskId);
		}
		return null;
	}

	public void updateTask(Task task) {
		taskDao.updateTask(task);
	}

	private void addMemberTasks(String memberId) {
		MemberDbo member = memberDboDao.findMemberById(memberId);
		if (member != null) {
			long releaseTime = 0;
			if (member.getReleaseTaskTime() != null) {
				releaseTime = member.getReleaseTaskTime();
			}
			long amount = taskDocumentHistoryDao.getAmount(releaseTime);
			int size = 300;
			long pageNum = amount % size > 0 ? amount / size + 1 : amount / size;
			for (int page = 1; page <= pageNum; page++) {
				List<TaskDocumentHistory> taskList = taskDocumentHistoryDao.findTaskByReleaseTime(page, size,
						releaseTime);
				for (TaskDocumentHistory taskHistory : taskList) {
					if (hasCriterion(member, taskHistory)) {
						Task task = new Task();
						task.setTaskId(taskHistory.getId());
						task.setMemberId(member.getId());
						task.setType(taskHistory.getType());
						task.setRewardGold(taskHistory.getRewardGold());
						task.setRewardScore(taskHistory.getRewardScore());
						task.setRewardVip(taskHistory.getRewardVip());
						task.setTaskState(TaskState.DOINGTASK);
						task.setTargetNum(taskHistory.getTargetNum());
						task.setFinishNum(0);
						task.setTarget(TargetType.getITargetByTaskHistory(taskHistory));
						taskDao.addTask(task);
					}
					member.setReleaseTaskTime(taskHistory.getReleaseTime());
				}
			}
			// 更新任务游标
			memberDboDao.updateMemberDbo(member);
		}
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
