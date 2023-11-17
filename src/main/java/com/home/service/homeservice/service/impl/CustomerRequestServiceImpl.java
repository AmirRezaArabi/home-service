package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.exception.IdIsNotExist;
import lombok.RequiredArgsConstructor;
import com.home.service.homeservice.repository.CustomerRequestRepository;
import com.home.service.homeservice.service.CustomerRequestService;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service

public class CustomerRequestServiceImpl implements CustomerRequestService {
    private final CustomerRequestRepository customerRequestRepository;
    @Override
    public CustomerRequest saveOrUpdate(CustomerRequest customerRequest) {
        return customerRequestRepository.save(customerRequest);
    }

    @Override
    public String delete(CustomerRequest customerRequest) {
        customerRequestRepository.delete(customerRequest);
        try {
            return customerRequest.getCustomer().getUsername();
        }
        catch (Exception e)
        {
            return "DELETED BUT CUSTOMER REQUEST DONT HAVE ANY CUSTOMER";
        }

    }

    @Override
    public CustomerRequest findById(Long id) {
        return customerRequestRepository.findById(id).orElseThrow(()->new IdIsNotExist("the id not found"));
    }

    @Override
    public List<CustomerRequest> findAll() {
        return customerRequestRepository.findAll();
    }

    @Override
    public List<CustomerRequest> findAllByRequest_status(REQUEST_STATUS request_status){
        return customerRequestRepository.findAllByStatus(request_status);
    }
}
