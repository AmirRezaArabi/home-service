package com.home.service.homeservice.service;


import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CustomerRequestService {

    CustomerRequest saveOrUpdate (CustomerRequest customerRequest);
    String delete (CustomerRequest customerRequest);
    CustomerRequest findById (Long id);
    List<CustomerRequest> findAll();

    List<CustomerRequest> findAllByRequest_status(REQUEST_STATUS request_status);

}
