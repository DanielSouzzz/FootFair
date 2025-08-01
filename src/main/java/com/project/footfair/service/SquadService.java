package com.project.footfair.service;

import com.project.footfair.entity.Player;
import com.project.footfair.entity.Squad;
import com.project.footfair.repository.PlayerRepository;
import com.project.footfair.repository.SquadRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class SquadService {
    private final SquadRepository squadRepository;
    private final PlayerRepository playerRepository;

    public SquadService(SquadRepository squadRepository, PlayerRepository playerRepository) {
        this.squadRepository = squadRepository;
        this.playerRepository = playerRepository;
    }

    public Squad createSquad(Squad squad){
        playerRepository.findById(squad.getCreate_player_id())
                .orElseThrow(()-> new ValidationException("User not found!"));

        Squad newSquad = squadRepository.save(squad);
        return newSquad;
    }
}
