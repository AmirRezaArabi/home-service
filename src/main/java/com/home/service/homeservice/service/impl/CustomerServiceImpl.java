package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.exception.TheInputInformationIsNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import com.home.service.homeservice.repository.CustomerRepository;
import com.home.service.homeservice.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.home.service.homeservice.validation.EntityValidator.isValid;

@RequiredArgsConstructor
@Service

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public Customer saveOrUpdate(Customer customer) {
        if (!isValid(customer))
            return null;
        return customerRepository.save(customer);
    }

    @Override
    public String delete(Customer customer) {
         customerRepository.delete(customer);
        return customer.getUserName();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findByUserName(String userName) {
        return customerRepository.findByUserName(userName);
    }

    @Override
    public Optional<Customer> chngePassword(String oldPassword, String userName, String newPassword) {
        Optional<Customer> byUserName = customerRepository.findByUserName(userName);
        if (byUserName.isEmpty() || !byUserName.get().getPassword().equals(oldPassword))
            throw new TheInputInformationIsNotValidException("The Input Information Is Not Valid ");
        Customer customer = byUserName.get();
        customer.setPassword(newPassword);
        return Optional.of(customerRepository.save(customer));
    }

    @Override
    public List<Customer> findAll() {
        return Streamable.of(customerRepository.findAll()).toList();
    }
}
