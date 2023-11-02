package com.home.service.homeservice.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public final class CustomerRequestDTO {
    @NotBlank(message = "password can not be empty or null")
    @Size(min = 8,max = 8,message = "the password length should 8 char")
    @Pattern(regexp = "^(?=.+\\d)(?=.+[a-zA-Z]).{8}$")
    private  String password;
    private  String name;
    @Column(unique = true)
    @NotBlank(message = "userName can not be empty or null")
    private  String userName;
    @Email
    @NotBlank(message = "email can not be empty or null")
    private  String emailAddress;


}
