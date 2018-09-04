package com.anbang.qipai.tasks.plan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.config.TargetType;
import com.anbang.qipai.tasks.config.TaskConfig;
import com.anbang.qipai.tasks.config.TaskDocumentHistoryState;
import com.anbang.qipai.tasks.config.TaskState;
import com.anbang.qipai.tasks.plan.bean.FinishTask;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.dao.FinishTaskDao;
import com.anbang.qipai.tasks.plan.dao.MemberDboDao;
import com.anbang.qipai.tasks.plan.dao.TaskDao;
import com.anbang.qipai.tasks.plan.dao.TaskDocumentHistoryDao;
import com.anbang.qipai.tasks.web.vo.TaskVO;

@Service
public class TaskService {

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private MemberDboDao memberDboDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private FinishTaskDao finishTaskDao;

	@Autowired
	private TaskDocumentHistoryDao taskDocumentHistoryDao;

	public List<TaskVO> queryMemberTasks(String memberId) {
		List<TaskVO> taskVos = new ArrayList<TaskVO>();
		// 添加新发布任务
		addMemberTasks(memberId);
		// 删除撤回任务
		removeMemberTasks(memberId);
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
		String memberId;
		String token = (String) params.get("token");
		if (token != null) {
			memberId = memberAuthService.getMemberIdBySessionId(token);
		} else {
			memberId = (String) params.get("memberId");
		}
		String[] memberIds = new String[0];
		Object ids = params.get("memberIds");
		if (ids != null) {
			memberIds = (String[]) ids;

		}
		if (memberIds.length > 0) {
			for (String id : memberIds) {
				List<Task> taskList = taskDao.findTaskByMemberId(id);
				for (Task task : taskList) {
					task.getTarget().updateTask(task, params);
				}
			}
		}
		if (memberId != null) {
			List<Task> taskList = taskDao.findTaskByMemberId(memberId);
			for (Task task : taskList) {
				task.getTarget().updateTask(task, params);
				taskDao.updateTask(task);
			}
		}
	}

	public Task getRewards(String taskId) {
		Task task = taskDao.findTaskById(taskId);
		if (task != null && TaskState.COMPLETETASK.equals(task.getTaskState())) {
			return task;
		}
		return null;
	}

	public void finishTask(String taskId) {
		Task task = taskDao.findTaskById(taskId);
		FinishTask finishTask = new FinishTask();
		finishTask.setTaskId(task.getTaskId());
		finishTask.setMemberId(task.getMemberId());
		finishTask.setName(task.getName());
		finishTask.setDesc(task.getDesc());
		finishTask.setType(task.getType());
		finishTask.setRewardGold(task.getRewardGold());
		finishTask.setRewardScore(task.getRewardScore());
		finishTask.setRewardVip(task.getRewardVip());
		finishTask.setTaskState(TaskState.FINISHTASK);
		finishTask.setTargetNum(task.getTargetNum());
		finishTask.setFinishNum(task.getFinishNum());
		finishTask.setTarget(task.getTarget());
		finishTask.setFinishTime(System.currentTimeMillis());
		finishTaskDao.addFinishTask(finishTask);
		task.setTaskState(TaskState.FINISHTASK);
		taskDao.updateTask(task);
		if (!"每日任务".equals(task.getType())) {
			taskDao.deleteTaskById(taskId);
		}
	}

	public void reset(String type) {
		long amount = taskDao.getAmountByType(type);
		int size = 300;
		long pageNum = amount % size > 0 ? amount / size + 1 : amount / size;
		for (int page = 1; page <= pageNum; page++) {
			List<Task> taskList = taskDao.findTasksByType(page, size, type);
			for (Task task : taskList) {
				task.getTarget().reset(task);
				taskDao.updateTask(task);
			}
		}
	}

	private void addMemberTasks(String memberId) {
		MemberDbo member = memberDboDao.findMemberById(memberId);
		if (member != null) {
			long releaseTime = member.getReleaseTime();
			long amount = taskDocumentHistoryDao.getAmountByReleaseTime(releaseTime);
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
						task.setName(taskHistory.getName());
						task.setDesc(taskHistory.getDesc());
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
					member.setReleaseTime(taskHistory.getReleaseTime());
				}
			}
			// 更新任务游标
			memberDboDao.updateReleaseTime(member.getId(), member.getReleaseTime());
		}
	}

	private void removeMemberTasks(String memberId) {
		MemberDbo member = memberDboDao.findMemberById(memberId);
		if (member != null) {
			List<Task> taskList = taskDao.findTaskByMemberId(memberId);
			for (Task task : taskList) {
				TaskDocumentHistory taskHistory = taskDocumentHistoryDao.findTaskById(task.getTaskId());
				if (TaskDocumentHistoryState.STOP.equals(taskHistory.getState())) {
					taskDao.deleteTaskById(task.getId());
				}
			}
		}
	}

	/**
	 * 判断是否满足接任务的条件
	 */
	private boolean hasCriterion(MemberDbo member, TaskDocumentHistory task) {
		if ("true".equals(task.getVip()) || "false".equals(task.getVip())) {
			if (task.getVip().equals(String.valueOf(member.isVip()))) {
				return true;
			}
			return false;
		}
		return true;
	}
}
