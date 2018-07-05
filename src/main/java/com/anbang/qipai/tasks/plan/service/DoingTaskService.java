package com.anbang.qipai.tasks.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.plan.dao.DoingTaskDao;
import com.anbang.qipai.tasks.plan.domain.DoingTask;

@Service
public class DoingTaskService {
	
	@Autowired
	private DoingTaskDao doingTaskDao;

	public List<DoingTask> findAllDoingTaskByMemberId(String memberId){
		return doingTaskDao.findAllDoingTaskByMemberId(memberId);
	}
	
	public List<DoingTask> findAllDoingTask(){
		return doingTaskDao.findAllDoingTask();
	}
	
	public void addDoingTask(DoingTask doingTask) {
		doingTaskDao.addDoingTask(doingTask);
	}
	
}
