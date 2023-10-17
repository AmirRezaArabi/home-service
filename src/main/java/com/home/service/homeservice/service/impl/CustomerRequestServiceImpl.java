package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.CustomerRequest;
import lombok.RequiredArgsConstructor;
import com.home.service.homeservice.repository.CustomerRequestRepository;
import com.home.service.homeservice.service.CustomerRequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.home.service.homeservice.validation.EntityValidator.isValid;
@RequiredArgsConstructor
@Service

public class CustomerRequestServiceImpl implements CustomerRequestService {
    private final CustomerRequestRepository customerRequestRepository;
    @Override
    public CustomerRequest saveOrUpdate(CustomerRequest customerRequest) {
        if (!isValid(customerRequest))
            return null;
        return customerRequestRepository.save(customerRequest);
    }

    @Override
    public String delete(CustomerRequest customerRequest) {
        customerRequestRepository.delete(customerRequest);
        return customerRequest.getCustomer().getUserName();
    }

    @Override
    public Optional<CustomerRequest> findById(Long id) {
        return customerRequestRepository.findById(id);
    }

    @Override
    public List<CustomerRequest> findAll() {
        return customerRequestRepository.findAll();
    }
}
