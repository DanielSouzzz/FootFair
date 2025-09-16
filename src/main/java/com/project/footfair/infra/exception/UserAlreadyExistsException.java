package com.project.footfair.infra.exception;

public class UserAlreadyExistsException extends BaseException{

    public UserAlreadyExistsException( String email){
        super("Usuário com email "+ email + " já está cadastrado");
    }
}
