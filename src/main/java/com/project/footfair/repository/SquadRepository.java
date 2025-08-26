package com.project.footfair.repository;

import com.project.footfair.entity.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface SquadRepository extends JpaRepository <Squad, Long>{

    @Query(value = "select * from player p where p.invite_code=:code limit 1", nativeQuery = true)
    Optional<Squad> findInviteCode(PathVariable code);
}
