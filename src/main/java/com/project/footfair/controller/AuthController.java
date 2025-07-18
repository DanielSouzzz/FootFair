package com.project.footfair.controller;

import com.project.footfair.dto.*;
import com.project.footfair.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO, UriComponentsBuilder uriBuilder){
        LoginResponseDTO responseDTO = authService.login(loginRequestDTO);
        UriComponents uri = uriBuilder.path("/api/auth/login/{id}").buildAndExpand(responseDTO.getId());

        return ResponseEntity.created(uri.toUri()).body(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterRequestDTO dto, UriComponentsBuilder uriBuilder){
        LoginResponseDTO responseDTO = authService.register(dto);
        UriComponents uri = uriBuilder.path("/api/auth/login/{id}").buildAndExpand(responseDTO.getId());
        return ResponseEntity.created(uri.toUri()).body(responseDTO);
    }

}
