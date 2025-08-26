package com.project.footfair.repository;

import com.project.footfair.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByEmail(String email);

    @Query(value = "select exists(select 1 from player_squad ps where ps.player_id = :player_id)", nativeQuery = true)
    Boolean findPlayerInSquadById(@Param("player_id") Long player_id);
}
