package com.nuist.test.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nuist.test.Entity.PlayerTable;

public interface PlayerDAO extends JpaRepository<PlayerTable, Integer> {

	PlayerTable findByPid(Integer pid);

	PlayerTable findByUsernameAndPassword(String username, String password);
}
