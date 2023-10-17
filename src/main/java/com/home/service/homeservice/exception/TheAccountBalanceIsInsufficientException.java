package com.home.service.homeservice.exception;

public class TheAccountBalanceIsInsufficientException extends RuntimeException{
    public TheAccountBalanceIsInsufficientException(String message) {
        super(message);
    }
}
