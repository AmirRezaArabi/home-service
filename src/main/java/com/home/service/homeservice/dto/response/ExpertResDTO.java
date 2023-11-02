package com.home.service.homeservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpertResDTO {
    private  Long id;
    private  String password;
    private  String name;
    private  String userName;
    private  String emailAddress;
}
