package com.avinash.projects.soshell.user.exeptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String msg){
        super(msg);
    }
}
