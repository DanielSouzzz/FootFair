package com.project.footfair.service;

import com.project.footfair.dto.*;
import com.project.footfair.entity.Player;
import com.project.footfair.infra.security.TokenService;
import com.project.footfair.mapper.UserAuthMapper;
import com.project.footfair.repository.PlayerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final PlayerRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthMapper userAuthMapper;

    public AuthService(PlayerRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder, UserAuthMapper userAuthMapper) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.userAuthMapper = userAuthMapper;
    }

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        Player user = this.userRepository.findByEmail(loginRequestDTO.email()).orElseThrow(() -> new RuntimeException("User not found."));

        boolean passwordMatches = passwordEncoder.matches(loginRequestDTO.password(), user.getPassword());
        if(!passwordMatches){
            throw new RuntimeException("Senha incorreta.");
        }
        String token = tokenService.genereteToken(user);

        LoginResponseDTO loginResponseDTO = userAuthMapper.toResponseDTO(user);
        loginResponseDTO.setToken(token);

        return loginResponseDTO;
    }

    @Transactional
    public LoginResponseDTO register(RegisterRequestDTO dto) {
        Player user = userAuthMapper.toPlayerEntity(dto);
        user = userRepository.save(user);
        return userAuthMapper.toResponseDTO(user);
    }
}
