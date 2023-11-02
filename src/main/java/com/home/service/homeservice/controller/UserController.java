package com.home.service.homeservice.controller;

import com.home.service.homeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    ResponseEntity<?> login(){
//
//    }
//    ResponseEntity<?> signIn(){
//
//    }
}
