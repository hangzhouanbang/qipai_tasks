package com.anbang.qipai.tasks.plan.service;

import java.util.Map;

import com.anbang.qipai.tasks.plan.domain.MemberDbo;
import com.anbang.qipai.tasks.plan.domain.TaskDocumentHistory;

public class TaskService {

	public boolean hasCriterion(MemberDbo member, TaskDocumentHistory task) {
		if(task.getVip() == member.getVip()) {
			return true;
		}
		if(task.getVip() == null) {
			return true;
		}
		return false;
	}

	public void updateTask(String memberId, Map<String, Integer> params) {

	}

	public boolean checkTask(MemberDbo member) {
		return false;
	}
}
