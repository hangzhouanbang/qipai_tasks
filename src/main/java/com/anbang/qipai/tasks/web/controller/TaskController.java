package com.anbang.qipai.tasks.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.tasks.config.TaskConfig;
import com.anbang.qipai.tasks.msg.service.FinishTasksMsgService;
import com.anbang.qipai.tasks.msg.service.MemberGoldsMsgService;
import com.anbang.qipai.tasks.msg.service.MemberHongbaodianMsgService;
import com.anbang.qipai.tasks.msg.service.MemberScoresMsgService;
import com.anbang.qipai.tasks.msg.service.MemberVIPMsgService;
import com.anbang.qipai.tasks.msg.service.TasksMsgService;
import com.anbang.qipai.tasks.plan.bean.FinishedTask;
import com.anbang.qipai.tasks.plan.bean.RewardType;
import com.anbang.qipai.tasks.plan.bean.Task;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistory;
import com.anbang.qipai.tasks.plan.bean.TaskDocumentHistoryState;
import com.anbang.qipai.tasks.plan.bean.TaskType;
import com.anbang.qipai.tasks.plan.service.MemberAuthService;
import com.anbang.qipai.tasks.plan.service.MemberInvitationRecordService;
import com.anbang.qipai.tasks.plan.service.MemberLoginRecordService;
import com.anbang.qipai.tasks.plan.service.TaskDocumentHistoryService;
import com.anbang.qipai.tasks.plan.service.TaskService;
import com.anbang.qipai.tasks.remote.service.QipaiHongbaoRemoteService;
import com.anbang.qipai.tasks.remote.vo.CommonRemoteVO;
import com.anbang.qipai.tasks.util.IPUtil;
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
	private MemberInvitationRecordService memberInvitationRecordService;

	@Autowired
	private MemberLoginRecordService memberLoginRecordService;

	@Autowired
	private TasksMsgService tasksMsgService;

	@Autowired
	private FinishTasksMsgService finishTasksMsgService;

	@Autowired
	private MemberGoldsMsgService memberGoldsMsgService;

	@Autowired
	private MemberScoresMsgService memberScoresMsgService;

	@Autowired
	private MemberVIPMsgService memberVIPMsgService;

	@Autowired
	private MemberHongbaodianMsgService memberHongbaodianMsgService;

	@Autowired
	private QipaiHongbaoRemoteService qipaiHongbaoRemoteService;

	@RequestMapping("/query_first_hongbao")
	public CommonVO queryFirstHongbao(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		vo.setData(data);
		data.put("hasTask", false);
		if (!taskService.queryMemberTasks(memberId).isEmpty()) {
			data.put("hasTask", true);
		}
		if (memberLoginRecordService.countLoginRecordByMemberId(memberId) <= 1) {
			Task task = taskService.queryFirstHongbao(memberId);
			data.put("taskId", task.getId());
			data.put("rewardUrl", task.getRewardUrl());
		}
		return vo;
	}

	/**
	 * 查询任务类型、种类
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
	 */
	@RequestMapping("/withdraw")
	public CommonVO withdrawTask(@RequestBody String[] taskIds) {
		CommonVO vo = new CommonVO();
		taskDocumentHistoryService.withdrawTaskDocumentHistory(taskIds);
		tasksMsgService.withdrawTaskDocumentHistory(taskIds);
		return vo;
	}

	/**
	 * 查询玩家个人任务
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
	 * 领取“赢得游戏”任务奖励
	 */
	@RequestMapping("/wingame_reward")
	public CommonVO getWinGamesTaskReward(HttpServletRequest request, String taskId) {
		CommonVO vo = new CommonVO();
		Task task = taskService.findTaskById(taskId);
		if (!task.getTaskName().equals("赢得游戏")) {
			vo.setSuccess(false);
			vo.setMsg("invalid type");
			return vo;
		}
		String reqIP = IPUtil.getRealIp(request);
		FinishedTask finishTask = taskService.finishTask(taskId, reqIP);
		if (finishTask == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid task");
			return vo;
		}
		RewardType rewardType = task.getRewardType();// 奖励类型
		// kafka无法深层序列化
		finishTask.getTask().setTarget(null);
		finishTasksMsgService.finishTask(finishTask);
		if (rewardType.equals(RewardType.HONGBAORMB)) {// 现金红包
			double rewardNum = task.getRewardNum();// 奖励数量
			CommonRemoteVO rvo = qipaiHongbaoRemoteService.hongbao_give_to_member(task.getMemberId(), rewardNum,
					"task_reward", reqIP);
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		} else {
			getReward(finishTask, reqIP);
		}
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("rewardType", finishTask.getRewardType());
		data.put("rewardNum", finishTask.getRewardNum());
		return vo;
	}

	/**
	 * 领取“邀请新玩家”任务奖励
	 */
	@RequestMapping("/invite_newmember_reward")
	public CommonVO getInviteNewMemberTaskReward(HttpServletRequest request, String taskId) {
		CommonVO vo = new CommonVO();
		Task task = taskService.findTaskById(taskId);
		if (!task.getTaskName().equals("邀请新玩家")) {
			vo.setSuccess(false);
			vo.setMsg("invalid type");
			return vo;
		}
		String reqIP = IPUtil.getRealIp(request);
		FinishedTask finishTask = taskService.finishTask(taskId, reqIP);
		if (finishTask == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid task");
			return vo;
		}
		int invitaionNum = memberInvitationRecordService.countInvitationByMemberId(finishTask.getMemberId());
		if (invitaionNum < finishTask.getTask().getTargetNum()) {
			// 只有满足邀请条件才发放奖励
			vo.setSuccess(false);
			return vo;
		}
		RewardType rewardType = task.getRewardType();// 奖励类型
		// kafka无法深层序列化
		finishTask.getTask().setTarget(null);
		finishTasksMsgService.finishTask(finishTask);
		if (rewardType.equals(RewardType.HONGBAORMB)) {// 现金红包
			double rewardNum = task.getRewardNum();// 奖励数量
			CommonRemoteVO rvo = qipaiHongbaoRemoteService.hongbao_give_to_member(task.getMemberId(), rewardNum,
					"task_reward", reqIP);
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		} else {
			getReward(finishTask, reqIP);
		}
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("rewardType", finishTask.getRewardType());
		data.put("rewardNum", finishTask.getRewardNum());
		return vo;
	}

	/**
	 * 更新”分享好友“任务
	 */
	@RequestMapping("/update_firends_reward")
	public CommonVO updateShareFirendsTask(String token, int finishNum) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		taskService.updateTask(memberId, "分享好友", finishNum);
		return vo;
	}

	/**
	 * 领取“分享好友”任务奖励
	 */
	@RequestMapping("/share_firends_reward")
	public CommonVO getShareFirendsTaskReward(HttpServletRequest request, String taskId) {
		CommonVO vo = new CommonVO();
		Task task = taskService.findTaskById(taskId);
		if (!task.getTaskName().equals("分享好友")) {
			vo.setSuccess(false);
			vo.setMsg("invalid type");
			return vo;
		}
		String reqIP = IPUtil.getRealIp(request);
		FinishedTask finishTask = taskService.finishTask(taskId, reqIP);
		if (finishTask == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid task");
			return vo;
		}
		RewardType rewardType = task.getRewardType();// 奖励类型
		// kafka无法深层序列化
		finishTask.getTask().setTarget(null);
		finishTasksMsgService.finishTask(finishTask);
		if (rewardType.equals(RewardType.HONGBAORMB)) {// 现金红包
			double rewardNum = task.getRewardNum();// 奖励数量
			CommonRemoteVO rvo = qipaiHongbaoRemoteService.hongbao_give_to_member(task.getMemberId(), rewardNum,
					"task_reward", reqIP);
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		} else {
			getReward(finishTask, reqIP);
		}
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("rewardType", finishTask.getRewardType());
		data.put("rewardNum", finishTask.getRewardNum());
		return vo;
	}

	/**
	 * 更新“分享朋友圈“任务
	 */
	@RequestMapping("/update_firends_circle_reward")
	public CommonVO updateShareFirendsCircleTask(String token, int finishNum) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		taskService.updateTask(memberId, "分享朋友圈", finishNum);
		return vo;
	}

	/**
	 * 领取“分享朋友圈”任务奖励
	 */
	@RequestMapping("/share_firends_circle_reward")
	public CommonVO getShareFirendsCircleTaskReward(HttpServletRequest request, String taskId) {
		CommonVO vo = new CommonVO();
		Task task = taskService.findTaskById(taskId);
		if (!task.getTaskName().equals("分享朋友圈")) {
			vo.setSuccess(false);
			vo.setMsg("invalid type");
			return vo;
		}
		String reqIP = IPUtil.getRealIp(request);
		FinishedTask finishTask = taskService.finishTask(taskId, reqIP);
		if (finishTask == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid task");
			return vo;
		}
		RewardType rewardType = task.getRewardType();// 奖励类型
		// kafka无法深层序列化
		finishTask.getTask().setTarget(null);
		finishTasksMsgService.finishTask(finishTask);
		if (rewardType.equals(RewardType.HONGBAORMB)) {// 现金红包
			double rewardNum = task.getRewardNum();// 奖励数量
			CommonRemoteVO rvo = qipaiHongbaoRemoteService.hongbao_give_to_member(task.getMemberId(), rewardNum,
					"task_reward", reqIP);
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		} else {
			getReward(finishTask, reqIP);
		}
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("rewardType", finishTask.getRewardType());
		data.put("rewardNum", finishTask.getRewardNum());
		return vo;
	}

	/**
	 * 领取“成为会员”任务奖励
	 */
	@RequestMapping("/vip_reward")
	public CommonVO getBecomeVIPTaskReward(HttpServletRequest request, String taskId) {
		CommonVO vo = new CommonVO();
		Task task = taskService.findTaskById(taskId);
		if (!task.getTaskName().equals("成为会员")) {
			vo.setSuccess(false);
			vo.setMsg("invalid type");
			return vo;
		}
		String reqIP = IPUtil.getRealIp(request);
		FinishedTask finishTask = taskService.finishTask(taskId, reqIP);
		if (finishTask == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid task");
			return vo;
		}
		RewardType rewardType = task.getRewardType();// 奖励类型
		// kafka无法深层序列化
		finishTask.getTask().setTarget(null);
		finishTasksMsgService.finishTask(finishTask);
		if (rewardType.equals(RewardType.HONGBAORMB)) {// 现金红包
			double rewardNum = task.getRewardNum();// 奖励数量
			CommonRemoteVO rvo = qipaiHongbaoRemoteService.hongbao_give_to_member(task.getMemberId(), rewardNum,
					"task_reward", reqIP);
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		} else {
			getReward(finishTask, reqIP);
		}
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("rewardType", finishTask.getRewardType());
		data.put("rewardNum", finishTask.getRewardNum());
		return vo;
	}

	/**
	 * 领取“红包点福利”任务奖励
	 */
	@RequestMapping("/hongbaodian_reward")
	public CommonVO getHongbaodianRewardTaskReward(HttpServletRequest request, String taskId) {
		CommonVO vo = new CommonVO();
		Task task = taskService.findTaskById(taskId);
		if (!task.getTaskName().equals("红包点福利")) {
			vo.setSuccess(false);
			vo.setMsg("invalid type");
			return vo;
		}
		String reqIP = IPUtil.getRealIp(request);
		FinishedTask finishTask = taskService.finishTask(taskId, reqIP);
		if (finishTask == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid task");
			return vo;
		}
		RewardType rewardType = task.getRewardType();// 奖励类型
		// kafka无法深层序列化
		finishTask.getTask().setTarget(null);
		finishTasksMsgService.finishTask(finishTask);
		if (rewardType.equals(RewardType.HONGBAORMB)) {// 现金红包
			double rewardNum = task.getRewardNum();// 奖励数量
			CommonRemoteVO rvo = qipaiHongbaoRemoteService.hongbao_give_to_member(task.getMemberId(), rewardNum,
					"task_reward", reqIP);
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		} else {
			getReward(finishTask, reqIP);
		}
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("rewardType", finishTask.getRewardType());
		data.put("rewardNum", finishTask.getRewardNum());
		return vo;
	}

	/**
	 * 领取“新春福利1”任务奖励
	 */
	@RequestMapping("/newyear_reward")
	public CommonVO getNewYearRewardOneTaskReward(HttpServletRequest request, String taskId) {
		CommonVO vo = new CommonVO();
		Task task = taskService.findTaskById(taskId);
		if (!task.getTaskName().equals("新春福利1")) {
			vo.setSuccess(false);
			vo.setMsg("invalid type");
			return vo;
		}
		String reqIP = IPUtil.getRealIp(request);
		FinishedTask finishTask = taskService.finishTask(taskId, reqIP);
		if (finishTask == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid task");
			return vo;
		}
		RewardType rewardType = task.getRewardType();// 奖励类型
		// kafka无法深层序列化
		finishTask.getTask().setTarget(null);
		finishTasksMsgService.finishTask(finishTask);
		if (rewardType.equals(RewardType.HONGBAORMB)) {// 现金红包
			double rewardNum = task.getRewardNum();// 奖励数量
			CommonRemoteVO rvo = qipaiHongbaoRemoteService.hongbao_give_to_member(task.getMemberId(), rewardNum,
					"task_reward", reqIP);
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		} else {
			getReward(finishTask, reqIP);
		}
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("rewardType", finishTask.getRewardType());
		data.put("rewardNum", finishTask.getRewardNum());
		return vo;
	}

	/**
	 * 领取“完成小盘游戏”任务奖励
	 */
	@RequestMapping("/pangame_reward")
	public CommonVO getPlayPanGameTaskReward(HttpServletRequest request, String taskId) {
		CommonVO vo = new CommonVO();
		Task task = taskService.findTaskById(taskId);
		if (!task.getTaskName().equals("完成小盘游戏")) {
			vo.setSuccess(false);
			vo.setMsg("invalid type");
			return vo;
		}
		String reqIP = IPUtil.getRealIp(request);
		FinishedTask finishTask = taskService.finishTask(taskId, reqIP);
		if (finishTask == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid task");
			return vo;
		}
		RewardType rewardType = task.getRewardType();// 奖励类型
		// kafka无法深层序列化
		finishTask.getTask().setTarget(null);
		finishTasksMsgService.finishTask(finishTask);
		if (rewardType.equals(RewardType.HONGBAORMB)) {// 现金红包
			double rewardNum = task.getRewardNum();// 奖励数量
			CommonRemoteVO rvo = qipaiHongbaoRemoteService.hongbao_give_to_member(task.getMemberId(), rewardNum,
					"task_reward", reqIP);
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		} else {
			getReward(finishTask, reqIP);
		}
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("rewardType", finishTask.getRewardType());
		data.put("rewardNum", finishTask.getRewardNum());
		return vo;
	}

	/**
	 * 领取“俏皮话”任务奖励
	 */
	@RequestMapping("/qiaopihua_reward")
	public CommonVO getQiaopihuaTaskReward(HttpServletRequest request, String taskId) {
		CommonVO vo = new CommonVO();
		Task task = taskService.findTaskById(taskId);
		if (!task.getTaskName().equals("俏皮话")) {
			vo.setSuccess(false);
			vo.setMsg("invalid type");
			return vo;
		}
		String reqIP = IPUtil.getRealIp(request);
		FinishedTask finishTask = taskService.finishTask(taskId, reqIP);
		if (finishTask == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid task");
			return vo;
		}
		RewardType rewardType = task.getRewardType();// 奖励类型
		// kafka无法深层序列化
		finishTask.getTask().setTarget(null);
		finishTasksMsgService.finishTask(finishTask);
		if (rewardType.equals(RewardType.HONGBAORMB)) {// 现金红包
			double rewardNum = task.getRewardNum();// 奖励数量
			CommonRemoteVO rvo = qipaiHongbaoRemoteService.hongbao_give_to_member(task.getMemberId(), rewardNum,
					"task_reward", reqIP);
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		} else {
			getReward(finishTask, reqIP);
		}
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("rewardType", finishTask.getRewardType());
		data.put("rewardNum", finishTask.getRewardNum());
		return vo;
	}

	/**
	 * 领取“对局任务”任务奖励
	 */
	@RequestMapping("/jugame_reward")
	public CommonVO getFinishJuGameTaskReward(HttpServletRequest request, String taskId) {
		CommonVO vo = new CommonVO();
		Task task = taskService.findTaskById(taskId);
		if (!task.getTaskName().equals("对局任务")) {
			vo.setSuccess(false);
			vo.setMsg("invalid type");
			return vo;
		}
		String reqIP = IPUtil.getRealIp(request);
		FinishedTask finishTask = taskService.finishTask(taskId, reqIP);
		if (finishTask == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid task");
			return vo;
		}
		RewardType rewardType = task.getRewardType();// 奖励类型
		// kafka无法深层序列化
		finishTask.getTask().setTarget(null);
		finishTasksMsgService.finishTask(finishTask);
		if (rewardType.equals(RewardType.HONGBAORMB)) {// 现金红包
			double rewardNum = task.getRewardNum();// 奖励数量
			CommonRemoteVO rvo = qipaiHongbaoRemoteService.hongbao_give_to_member(task.getMemberId(), rewardNum,
					"task_reward", reqIP);
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		} else {
			getReward(finishTask, reqIP);
		}
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("rewardType", finishTask.getRewardType());
		data.put("rewardNum", finishTask.getRewardNum());
		return vo;
	}

	/**
	 * 领取“大局正分”任务奖励
	 */
	@RequestMapping("/juscore_reward")
	public CommonVO getPositiveScoreOfJuTaskReward(HttpServletRequest request, String taskId) {
		CommonVO vo = new CommonVO();
		Task task = taskService.findTaskById(taskId);
		if (!task.getTaskName().equals("大局正分")) {
			vo.setSuccess(false);
			vo.setMsg("invalid type");
			return vo;
		}
		String reqIP = IPUtil.getRealIp(request);
		FinishedTask finishTask = taskService.finishTask(taskId, reqIP);
		if (finishTask == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid task");
			return vo;
		}
		RewardType rewardType = task.getRewardType();// 奖励类型
		// kafka无法深层序列化
		finishTask.getTask().setTarget(null);
		finishTasksMsgService.finishTask(finishTask);
		if (rewardType.equals(RewardType.HONGBAORMB)) {// 现金红包
			double rewardNum = task.getRewardNum();// 奖励数量
			CommonRemoteVO rvo = qipaiHongbaoRemoteService.hongbao_give_to_member(task.getMemberId(), rewardNum,
					"task_reward", reqIP);
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		} else {
			getReward(finishTask, reqIP);
		}
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("rewardType", finishTask.getRewardType());
		data.put("rewardNum", finishTask.getRewardNum());
		return vo;
	}

	/**
	 * 领奖
	 */
	private void getReward(FinishedTask task, String reqIP) {
		RewardType rewardType = task.getRewardType();// 奖励类型
		double rewardNum = task.getRewardNum();// 奖励数量
		if (rewardType.equals(RewardType.YUSHI)) {
			memberGoldsMsgService.giveGoldToMember(task.getMemberId(), (int) rewardNum, "task_reward");
		} else if (rewardType.equals(RewardType.LIQUAN)) {
			memberScoresMsgService.giveScoreToMember(task.getMemberId(), (int) rewardNum, "task_reward");
		} else if (rewardType.equals(RewardType.VIPTIME)) {
			memberVIPMsgService.rewardVip(task.getMemberId(), (long) rewardNum, "task_reward");
		} else if (rewardType.equals(RewardType.HONGBAODIAN)) {
			memberHongbaodianMsgService.giveHongbaodianToMember(task.getMemberId(), (int) rewardNum, "task_reward");
		} else {
		}
	}

	/**
	 * 重置每日任务
	 * 
	 */
	@Scheduled(cron = "0 0 0 * * ?") // 每天凌晨
	public void resetEveryDayTask() {
		taskService.reset(TaskType.EVERYDAY);
	}
}
