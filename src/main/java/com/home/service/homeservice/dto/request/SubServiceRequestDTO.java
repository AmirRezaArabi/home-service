package com.home.service.homeservice.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SubServiceRequestDTO {

    private String serviceName;

    private String name;

    private String description;

    private Long basePrice;
}
