package com.drsimple.jwtsecurity.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {

    @Query("SELECT p FROM Player p WHERE p.username = :username")
    List<Player> findByUsername(@Param("username") String username);
}