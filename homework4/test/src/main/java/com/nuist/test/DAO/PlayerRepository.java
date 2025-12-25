package com.nuist.test.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nuist.test.Entity.PlayerTable;

public interface PlayerRepository
        extends JpaRepository<PlayerTable, Integer> {

    Optional<PlayerTable> findByUsernameAndPassword(
            String username,
            String password
    );
}
