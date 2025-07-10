package com.project.footfair.service;

import com.project.footfair.dto.PlayerResponseDTO;
import com.project.footfair.dto.RegisterPlayerDTO;
import com.project.footfair.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public PlayerResponseDTO register(RegisterPlayerDTO dto) {
        return null;
    }
}
