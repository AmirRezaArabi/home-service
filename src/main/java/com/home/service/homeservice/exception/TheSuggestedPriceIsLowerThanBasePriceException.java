package com.home.service.homeservice.exception;

public class TheSuggestedPriceIsLowerThanBasePriceException extends RuntimeException{
    public TheSuggestedPriceIsLowerThanBasePriceException(String message) {
        super(message);
    }
}
