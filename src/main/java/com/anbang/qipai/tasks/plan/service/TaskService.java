package com.anbang.qipai.tasks.plan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.config.TargetType;
import com.anbang.qipai.tasks.config.TaskConfig;
import com.anbang.qipai.tasks.plan.bean.FinishedTask;
import com.anbang.qipai.tasks.plan.bean.ITarget;
import com.anbang.qipai.tasks.plan.bean.MemberDbo;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistoryState;
import com.anbang.qipai.tasks.plan.bean.TaskState;
import com.anbang.qipai.tasks.plan.bean.TaskType;
import com.anbang.qipai.tasks.plan.dao.FinishTaskDao;
import com.anbang.qipai.tasks.plan.dao.MemberDboDao;
import com.anbang.qipai.tasks.plan.dao.TaskDao;
import com.anbang.qipai.tasks.plan.dao.TaskDocumentHistoryDao;
import com.anbang.qipai.tasks.web.vo.TaskVO;

@Service
public class TaskService {

	@Autowired
	private MemberDboDao memberDboDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private FinishTaskDao finishTaskDao;

	@Autowired
	private TaskDocumentHistoryDao taskDocumentHistoryDao;

	/**
	 * 临时用 查询玩家是否可以领取红包
	 */
	public Task queryFirstHongbao(String memberId) {
		Task task = null;
		MemberDbo member = memberDboDao.findMemberById(memberId);
		if (member != null) {
			// 添加新发布任务
			addMemberTasks(memberId);
			List<Task> taskList = taskDao.findTaskByMemberId(memberId);
			// 删除撤回任务
			removeMemberTasks(taskList);
			// 删除过期任务
			removeMemberTasksForLimitTime(taskList);

			List<Task> tasks = taskDao.findTaskByMemberIdAndTaskName(memberId, "新春福利1");
			if (!tasks.isEmpty()) {
				task = tasks.get(0);
			}
		}
		return task;
	}

	/**
	 * 查询玩家任务
	 */
	public List<TaskVO> queryMemberTasks(String memberId) {
		List<TaskVO> taskVos = new ArrayList<TaskVO>();
		MemberDbo member = memberDboDao.findMemberById(memberId);
		if (member != null) {
			// 添加新发布任务
			addMemberTasks(memberId);
			List<Task> taskList = taskDao.findTaskByMemberId(memberId);
			// 删除撤回任务
			removeMemberTasks(taskList);
			// 删除过期任务
			removeMemberTasksForLimitTime(taskList);
			List<String> typeList = TaskConfig.typeList;
			for (String type : typeList) {
				TaskVO taskVo = new TaskVO();
				taskVo.setType(type);
				List<Task> tasks = taskDao.findTaskByMemberIdAndType(memberId, type);
				taskVo.setTaskList(tasks);
				taskVos.add(taskVo);
			}
		}
		return taskVos;
	}

	/**
	 * 更新任务
	 */
	public void updateTask(String memberId, String taskName, int finishNum) {
		MemberDbo member = memberDboDao.findMemberById(memberId);
		List<Task> taskList = taskDao.findTaskByMemberIdAndTaskName(memberId, taskName);
		for (Task task : taskList) {
			task.getTarget().updateTask(task, member, finishNum);
			taskDao.updateTask(task);
		}
	}

	/**
	 * 完成任务
	 */
	public FinishedTask finishTask(String taskId, String repIP) {
		Task task = taskDao.findTaskById(taskId);
		if (!TaskState.COMPLETETASK.equals(task.getTaskState())) {// 只有完成任务才能领奖
			return null;
		}
		MemberDbo member = memberDboDao.findMemberById(task.getMemberId());
		task.getTarget().finishTask(task);
		taskDao.updateTask(task);
		FinishedTask finishedTask = new FinishedTask();
		finishedTask.setMemberId(member.getId());
		finishedTask.setNickname(member.getNickname());
		finishedTask.setTaskId(task.getTaskId());
		finishedTask.setRewardType(task.getRewardType());
		finishedTask.setRewardNum(task.getRewardNum());
		finishedTask.setGetRewardIP(repIP);
		finishedTask.setTask(task);
		finishedTask.setFinishTime(System.currentTimeMillis());
		finishTaskDao.addFinishTask(finishedTask);
		if (!TaskType.EVERYDAY.equals(task.getTaskType())) {
			taskDao.deleteTaskById(task.getId());
		}
		return finishedTask;
	}

	/**
	 * 重置任务
	 */
	public void reset(TaskType taskType) {
		long amount = taskDao.getAmountByType(taskType);
		int size = 300;
		long pageNum = amount % size > 0 ? amount / size + 1 : amount / size;
		for (int page = 1; page <= pageNum; page++) {
			List<Task> taskList = taskDao.findTasksByType(page, size, taskType);
			for (Task task : taskList) {
				MemberDbo member = memberDboDao.findMemberById(task.getMemberId());
				task.getTarget().reset(task, member);
				taskDao.updateTask(task);
			}
		}
	}

	private void addMemberTasks(String memberId) {
		MemberDbo member = memberDboDao.findMemberById(memberId);
		if (member != null) {
			List<TaskDocumentHistory> taskList = taskDocumentHistoryDao.findTaskByState(TaskDocumentHistoryState.START);
			for (TaskDocumentHistory taskHistory : taskList) {
				// 已经存在的不再重复添加
				if (taskDao.findTaskByMemberIdAndTaskId(memberId, taskHistory.getId()) != null) {
					continue;
				}
				// 已经完成的不再添加
				if (finishTaskDao.findFinishTaskByMemberIdAndTaskId(memberId, taskHistory.getId()) != null) {
					continue;
				}
				Task task = new Task();
				task.setTaskId(taskHistory.getId());
				task.setMemberId(member.getId());
				task.setName(taskHistory.getName());
				task.setDesc(taskHistory.getDesc());
				task.setType(taskHistory.getType());
				task.setTaskName(taskHistory.getTaskName());
				task.setRewardType(taskHistory.getRewardType());
				task.setRewardNum(taskHistory.getRewardNum());
				task.setTargetNum(taskHistory.getTargetNum());
				task.setFinishNum(0);
				if (taskHistory.getLimitTime() > 0) {
					task.setDeadTime(System.currentTimeMillis() + taskHistory.getLimitTime());
				}
				task.setTaskType(taskHistory.getTaskType());
				ITarget target = TargetType.getITargetByTaskHistory(taskHistory);
				// 初始化
				target.init(task, member);
				task.setTarget(target);
				// 刷新状态
				task.getTarget().updateTask(task, member, 0);
				taskDao.addTask(task);
			}
		}
	}

	/**
	 * 删除撤回的任务
	 */
	private void removeMemberTasks(List<Task> taskList) {
		for (Task task : taskList) {
			TaskDocumentHistory taskHistory = taskDocumentHistoryDao.findTaskById(task.getTaskId());
			// 如果任务已经撤销或者不存在，则删除玩家任务
			if (taskHistory == null || TaskDocumentHistoryState.STOP.equals(taskHistory.getState())) {
				taskDao.deleteTaskById(task.getId());
			}
		}
	}

	/**
	 * 删除过期任务
	 */
	private void removeMemberTasksForLimitTime(List<Task> taskList) {
		for (Task task : taskList) {
			long currentTime = System.currentTimeMillis();
			// 如果任务到期，则删除玩家任务
			if (task.getDeadTime() > 0 && currentTime > task.getDeadTime()) {
				taskDao.deleteTaskById(task.getId());
			}
		}
	}

	/**
	 * 玩家状态改变需要过滤任务
	 */
	private void filterMemberTasks(String memberId, List<Task> taskList) {

	}

	/**
	 * 判断是否满足接任务的条件
	 */
	private boolean hasCriterion(MemberDbo member, TaskDocumentHistory task) {
		if (task.isVip()) {
			if (member.isVip()) {
				return true;
			}
			return false;
		}
		return true;
	}
}
