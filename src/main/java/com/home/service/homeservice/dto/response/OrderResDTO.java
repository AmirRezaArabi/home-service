package com.home.service.homeservice.dto.response;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.SubService;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class OrderResDTO {
    private Long id;
    private Long subServiceId;
    private Long customerId;
    private Long expertId;
    private REQUEST_STATUS request_status;
    private Long Price;
    private int duration;
    private String description;
    private LocalDate startDay;
    private String comment;
    private int score;

}
