package com.home.service.homeservice.utility;

import com.home.service.homeservice.exception.*;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNameOrPasswordDosNotExistException.class)
    public ResponseEntity<ErrorMessage> usernameOrPasswordDosNotExistException(UserNameOrPasswordDosNotExistException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidServiceNameExctetion.class)
    public ResponseEntity<?> invalidServiceName(InvalidServiceNameExctetion ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IdIsNotExist.class)
    public ResponseEntity<?> idIsNotExist(IdIsNotExist notExist) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notExist.getMessage());

    }

    @ExceptionHandler(NotValiedPasswordException.class)
    public ResponseEntity<?> invalidServiceName(NotValiedPasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(TheAccountBalanceIsInsufficientException.class)
    public ResponseEntity<?> invalidServiceName(TheAccountBalanceIsInsufficientException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(TheEmailNotFoundException.class)
    public ResponseEntity<?> invalidServiceName(TheEmailNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(TheFollowingServiceNameIsUsedRepeatedlyException.class)
    public ResponseEntity<?> invalidServiceName(TheFollowingServiceNameIsUsedRepeatedlyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(TheInputInformationIsNotValidException.class)
    public ResponseEntity<?> invalidServiceName(TheInputInformationIsNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(TheInputTimeIsNotValid.class)
    public ResponseEntity<?> invalidServiceName(TheInputTimeIsNotValid ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(TheNotConfirmedExpertException.class)
    public ResponseEntity<?> invalidServiceName(TheNotConfirmedExpertException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(TheSelectedServiceDoesNotExistException.class)
    public ResponseEntity<?> invalidServiceName(TheSelectedServiceDoesNotExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(TheSuggestedPriceIsLowerThanBasePriceException.class)
    public ResponseEntity<?> invalidServiceName(TheSuggestedPriceIsLowerThanBasePriceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(TheTokenNotFoundException.class)
    public ResponseEntity<?> invalidServiceName(TheTokenNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointer(NullPointerException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage() + ":");
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFound(UsernameNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(BadStatusException.class)
    public ResponseEntity<?> usernameNotFound(BadStatusException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<?> unauthorized(HttpClientErrorException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> unauthr(HttpClientErrorException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> io(IOException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> allException(RuntimeException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> allException(Exception ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
