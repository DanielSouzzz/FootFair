package com.project.footfair.controller;

import com.project.footfair.dto.PlayerResponseDTO;
import com.project.footfair.dto.RegisterPlayerDTO;
import com.project.footfair.service.PlayerService;
import org.springframework.web.bind.annotation.*;

// cadastro/entrada
@RestController
@RequestMapping("/api/players")
public class AuthController {
    private final PlayerService playerService;

    public AuthController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/register")
    public PlayerResponseDTO register(@RequestBody RegisterPlayerDTO dto){
        PlayerResponseDTO response = playerService.register(dto);
        return response;
    }
}
