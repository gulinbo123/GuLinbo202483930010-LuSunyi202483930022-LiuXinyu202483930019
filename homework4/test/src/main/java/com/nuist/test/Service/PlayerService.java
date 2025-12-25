package com.nuist.test.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuist.test.DAO.PlayerDAO;
import com.nuist.test.Entity.PlayerTable;
import com.nuist.test.Entity.WorldTable;

import java.util.Set;

@Service
public class PlayerService {

	@Autowired
	private PlayerDAO playerDAO;

	public boolean loginService(String username, String password) {
		return playerDAO.findByUsernameAndPassword(username, password) != null;
	}

	public Set<WorldTable> allWorlds(Integer pid) {
		PlayerTable playerTable = playerDAO.findByPid(pid);
		return playerTable.getWorlds();
	}
}
