package com.home.service.homeservice.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class ExpertReqDTO {
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
