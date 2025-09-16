package com.project.footfair.infra.exception;

public class PlayerAlreadyInSquadException extends BaseException{
    public PlayerAlreadyInSquadException(Long playerId, Long squadId){
        super("O jogador "+ playerId + " Já faz parte do squad " + squadId);
    }
}
