package com.anbang.qipai.tasks.plan.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.tasks.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.tasks.remote.vo.CommonRemoteVO;
import com.dml.users.UserSessionsManager;

@Component
public class MemberAuthService {

	@Autowired
	private UserSessionsManager userSessionsManager;

	@Autowired
	private QipaiMembersRemoteService qipaiMembersRemoteService;

	public String getMemberIdBySessionId(String sessionId) {

		String memberId = userSessionsManager.getUserIdBySessionId(sessionId);
		if (memberId == null) {
			CommonRemoteVO rvo = qipaiMembersRemoteService.auth_trytoken(sessionId);
			if (rvo.isSuccess()) {
				Map data = (Map) rvo.getData();
				memberId = (String) data.get("memberId");
				userSessionsManager.createEngrossSessionForUser(memberId, sessionId, System.currentTimeMillis(), 0);
			}
		}
		return memberId;

	}

}
