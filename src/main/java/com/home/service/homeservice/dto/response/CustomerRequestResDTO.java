package com.home.service.homeservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestResDTO {
    private Long id;
    private String subServiceName ;
    private Long price;
    private String customerUserName;
    private String description;
    private Timestamp time ;
    private String address;
}
