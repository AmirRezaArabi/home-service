package com.home.service.homeservice.dto.response;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Data
public final class CustomerResponseDTO {
    private  Long id;
    private  String password;
    private  String name;
    private  String userName;
    private  String emailAddress;


}