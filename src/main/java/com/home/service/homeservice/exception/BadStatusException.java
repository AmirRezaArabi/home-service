package com.home.service.homeservice.exception;

public class BadStatusException extends RuntimeException{
    public BadStatusException(String message) {
        super(message);
    }
}
