package com.project.footfair.infra.exception;

public abstract class BaseException extends RuntimeException{
    protected BaseException(String message){
        super(message);
    }
}
