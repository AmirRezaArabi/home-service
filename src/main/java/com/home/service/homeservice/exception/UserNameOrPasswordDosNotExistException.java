package com.home.service.homeservice.exception;

public class UserNameOrPasswordDosNotExistException extends RuntimeException{
    public UserNameOrPasswordDosNotExistException(String message) {
        super(message);
    }
}
