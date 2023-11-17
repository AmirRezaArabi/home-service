package com.home.service.homeservice.filter;


import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.domain.SubService;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilter {
    private Customer customer;
    private Expert expert;
    private LocalDate StartOrderTime;
    private LocalDate endOrderTime;
    private REQUEST_STATUS request_status;
    private Service service;
    private SubService subService;
}


