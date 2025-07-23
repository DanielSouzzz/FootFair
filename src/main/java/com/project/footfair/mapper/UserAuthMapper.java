package com.project.footfair.mapper;

import com.project.footfair.dto.LoginResponseDTO;
import com.project.footfair.dto.RegisterRequestDTO;
import com.project.footfair.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class UserAuthMapper {
        public LoginResponseDTO toResponseDTO(Player entity) {
            if(entity == null){
                return null;
            }

            return new LoginResponseDTO(
                    entity.getId(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getToken(),
                    entity.getRole(),
                    null
            );
        }
        public Player toPlayerEntity(RegisterRequestDTO dto){
            if( dto == null){
                return null;
            }

            var player = new Player();
            player.setName(dto.getName());
            player.setEmail(dto.getEmail());
            player.setPassword(dto.getPassword());
            return player;
        }
}
