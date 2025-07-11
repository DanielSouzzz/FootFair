package com.project.footfair.service;

import com.project.footfair.dto.*;
import com.project.footfair.entity.Player;
import com.project.footfair.infra.security.TokenService;
import com.project.footfair.mapper.UserLoginMapper;
import com.project.footfair.repository.PlayerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final PlayerRepository playerRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserLoginMapper userLoginMapper;

    public AuthService(PlayerRepository playerRepository, TokenService tokenService, PasswordEncoder passwordEncoder, UserLoginMapper userLoginMapper) {
        this.playerRepository = playerRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.userLoginMapper = userLoginMapper;
    }

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        Player user = this.playerRepository.findByEmail(loginRequestDTO.email()).orElseThrow(() -> new RuntimeException("User not found."));

        boolean passwordMatches = passwordEncoder.matches(loginRequestDTO.password(), user.getPassword());
        if(!passwordMatches){
            throw new RuntimeException("Senha incorreta.");
        }
        String token = tokenService.genereteToken(user);

        LoginResponseDTO loginResponseDTO = userLoginMapper.toResponseDTO(user);
        loginResponseDTO.setToken(token);

        return loginResponseDTO;
    }

    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO dto) {
        Player user =

        return null;
    }
}
