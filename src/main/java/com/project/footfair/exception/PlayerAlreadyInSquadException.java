package com.project.footfair.exception;

public class PlayerAlreadyInSquadException extends RuntimeException{
    public PlayerAlreadyInSquadException(String message){
        super(message);
    }
}
