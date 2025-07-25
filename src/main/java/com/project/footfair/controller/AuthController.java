package com.project.footfair.controller;

import com.project.footfair.dto.*;
import com.project.footfair.service.AuthService;
import com.project.footfair.service.LoginAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private final AuthService authService;
    private final LoginAttemptService loginAttemptService;

    public AuthController(AuthService authService, LoginAttemptService loginAttemptService)  {
        this.authService = authService;
        this.loginAttemptService = loginAttemptService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO, HttpServletRequest httpRequest){
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

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody @Valid RegisterRequestDTO dto, UriComponentsBuilder uriBuilder){
        LoginResponseDTO responseDTO = authService.register(dto);
        UriComponents uri = uriBuilder.path("/api/auth/login/{id}").buildAndExpand(responseDTO.getId());
        return ResponseEntity.created(uri.toUri()).body(responseDTO);
    }

}
