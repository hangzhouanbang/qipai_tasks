package com.anbang.qipai.tasks.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.tasks.plan.dao.FinishTaskDao;
import com.anbang.qipai.tasks.plan.domain.FinishTask;

@Service
public class FinishTaskService {
	
	@Autowired
	private FinishTaskDao finishTaskDao;
	
	public List<FinishTask> findAllFinishTaskByMemberId(String memberId){
		return finishTaskDao.findAllFinishTaskByMemberId(memberId);
	}
	
	public List<FinishTask> findAllFinishTask(){
		return finishTaskDao.findAllFinishTask();
	}
	
	public void addFinishTask(FinishTask finishTask) {
		finishTaskDao.addFinishTask(finishTask);
	}
	
	

}
