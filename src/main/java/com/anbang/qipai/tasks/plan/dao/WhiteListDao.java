package com.anbang.qipai.tasks.plan.dao;

import com.anbang.qipai.tasks.plan.bean.WhiteList;

public interface WhiteListDao {

	void insert(WhiteList wl);

	void update(WhiteList wl);

	void remove(String[] ids);

	WhiteList findByPlayerId(String playerId);
}
