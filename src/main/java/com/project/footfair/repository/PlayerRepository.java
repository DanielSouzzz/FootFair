package com.project.footfair.repository;

import com.project.footfair.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByEmail(String email);

    @Query(value = "select exists(select 1 from player_squad ps where ps.player_id = :player_id)", nativeQuery = true)
    Long existsPlayerInSquad(@Param("player_id") Long player_id);

    @Modifying
    @Query(value = "insert into player_squad (player_id, squad_id) values (:player_id, :squad_id)", nativeQuery = true)
    void joinPlayerInSquad(@Param("player_id") Long player_id, @Param("squad_id") Long squad_id);
}


