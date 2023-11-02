package com.home.service.homeservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class CustomerRequestReqDTO {
    private Long subServiceId ;
    private Long price;
    private String customerUserName;
    private String description;
    private Timestamp time ;
    private String address;
}
