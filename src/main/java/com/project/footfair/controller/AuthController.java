package com.project.footfair.controller;

import com.project.footfair.dto.*;
import com.project.footfair.service.AuthService;
import com.project.footfair.service.LoginAttemptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final LoginAttemptService loginAttemptService;

    public AuthController(AuthService authService, LoginAttemptService loginAttemptService)  {
        this.authService = authService;
        this.loginAttemptService = loginAttemptService;
    }

    @Operation(
        summary = "Realiza login do usuário",
        description = "Autentica o usuário e retorna o token de acesso. Bloqueia após muitas tentativas falhas."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Email ou senha inválidos"),
        @ApiResponse(responseCode = "429", description = "Muitas tentativas, usuário bloqueado temporariamente")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
        @RequestBody @Valid LoginRequestDTO loginRequestDTO,
        @Parameter(description = "Requisição HTTP para obter IP do cliente") HttpServletRequest httpRequest){
        String ip = getClientIP(httpRequest);
         if (loginAttemptService.isBlocked(ip)){
             return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                     .body(new LoginResponseDTO(null, null, null, null, -1,"Voce excedeu a quantidade maxima de tentativas. Tente novamente mais tarde!"));
         }

         try {
             LoginResponseDTO responseDTO = authService.login(loginRequestDTO);
             loginAttemptService.loginSucceeded(ip);
             return ResponseEntity.ok(responseDTO);
         }catch (Exception ex){
             loginAttemptService.loginFailed(ip);

             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDTO(null, null, null, null, -1, "Email ou senha invalidos."));
         }
    }

    private String getClientIP(HttpServletRequest httpRequest) {
        String xf = httpRequest.getHeader("X-Forwarded-For");
        return xf == null ? httpRequest.getRemoteAddr() : xf.split(",")[0];
    }

    @Operation(
        summary = "Registra um novo usuário",
        description = "Cria um novo usuário e retorna os dados do registro."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(
        @RequestBody @Valid RegisterRequestDTO dto,
        @Parameter(description = "Construtor de URI para resposta") UriComponentsBuilder uriBuilder){
        RegisterResponseDTO responseDTO = authService.register(dto);
        UriComponents uri = uriBuilder.path("/api/auth/login/{id}").buildAndExpand(responseDTO.getId());
        return ResponseEntity.created(uri.toUri()).body(responseDTO);
    }

}