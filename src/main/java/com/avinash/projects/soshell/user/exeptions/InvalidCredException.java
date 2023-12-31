package com.avinash.projects.soshell.user.exeptions;

public class InvalidCredException extends RuntimeException{
    public InvalidCredException(String msg){
        super(msg);
    }
}
