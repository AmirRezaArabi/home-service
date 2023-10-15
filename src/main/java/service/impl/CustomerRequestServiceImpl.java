package service.impl;

import domain.CustomerRequest;
import lombok.RequiredArgsConstructor;
import repository.CustomerRequestRepository;
import service.CustomerRequestService;

import java.util.List;
import java.util.Optional;

import static validation.EntityValidator.isValid;
@RequiredArgsConstructor
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
