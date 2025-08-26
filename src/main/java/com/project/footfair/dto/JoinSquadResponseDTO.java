package com.project.footfair.dto;

import com.project.footfair.entity.Player;
import com.project.footfair.entity.Squad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinSquadResponseDTO {
    private Player dataPlayer;
    private Squad dataSquad;
    private String message;
}
