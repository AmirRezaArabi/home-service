package com.home.service.homeservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubServiceResponseDTO {

    private Long id;

    private String serviceName;

    private String name;

    private String description;

    private Long basePrice;
}
