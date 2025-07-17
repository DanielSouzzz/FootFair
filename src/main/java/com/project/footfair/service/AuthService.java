package com.project.footfair.service;

import com.project.footfair.dto.*;
import com.project.footfair.entity.Player;
import com.project.footfair.infra.security.TokenService;
import com.project.footfair.mapper.UserAuthMapper;
import com.project.footfair.repository.PlayerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        String token = this.tokenService.genereteToken(user);

        LoginResponseDTO loginResponseDTO = this.userAuthMapper.toResponseDTO(user);
        loginResponseDTO.setToken(token);

        return loginResponseDTO;
    }

    @Transactional
    public LoginResponseDTO register(RegisterRequestDTO dto) {
        Optional<Player> existingUser = userRepository.findByEmail(dto.getEmail());
        if (existingUser.isPresent()){
            throw new RuntimeException("Email already registered");
        }
        Player user = userAuthMapper.toPlayerEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        this.userRepository.save(user);

        String token = this.tokenService.genereteToken(user);

        LoginResponseDTO loginResponseDTO = this.userAuthMapper.toResponseDTO(user);
        loginResponseDTO.setToken(token);

        return loginResponseDTO;
    }
}
