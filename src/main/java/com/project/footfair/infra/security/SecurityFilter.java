package com.project.footfair.infra.security;

import com.project.footfair.entity.Player;
import com.project.footfair.repository.PlayerRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final PlayerRepository playerRepository;

    public SecurityFilter(TokenService tokenService, PlayerRepository playerRepository) {
        this.tokenService = tokenService;
        this.playerRepository = playerRepository;
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if (login != null){
            Player player = playerRepository.findByEmail(login);
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            var authentication = new UsernamePasswordAuthenticationToken(player, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader != null){
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
