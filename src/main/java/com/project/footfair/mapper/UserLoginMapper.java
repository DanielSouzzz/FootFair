package com.project.footfair.mapper;

import com.project.footfair.dto.LoginResponseDTO;
import com.project.footfair.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class UserLoginMapper {
        public LoginResponseDTO toResponseDTO(Player entity) {
            if(entity == null){
                return null;
            }

            return new LoginResponseDTO(
                    entity.getId(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getToken(),
                    entity.getRole()
            );
        }
}
