package com.project.footfair.service;

import com.project.footfair.entity.Player;
import com.project.footfair.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService extends BaseService{
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(Player playerDTO){
        return playerRepository.save(playerDTO);
    }
}
