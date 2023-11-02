package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.exception.TheInputInformationIsNotValidException;
import com.home.service.homeservice.exception.UserNameOrPasswordDosNotExistException;
import com.home.service.homeservice.repository.CustomerRepository;
import com.home.service.homeservice.service.CustomerService;
import com.home.service.homeservice.service.WalletService;
import com.home.service.homeservice.utility.CustomerSpec;
import com.home.service.homeservice.utility.ExpertFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    private final WalletService walletService;


    @Override
    public Customer saveOrUpdate(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public String delete(Customer customer) {
         customerRepository.delete(customer);
        return customer.getUserName();
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
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
    public Optional<Customer> changePassword(String oldPassword, String userName, String newPassword) {
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

    @Override
    public Customer signIn(String userName, String password) {
        Optional<Customer> byUserName = customerRepository.findByUserName(userName);
        if (byUserName.isEmpty()||!byUserName.get().getPassword().equals(password))
            throw new UserNameOrPasswordDosNotExistException("UserName Or Password Dos Not Exist");
        return byUserName.get();
    }


    @Override
    public Customer signUp(Customer customer) {
        Wallet build = Wallet.builder().user(customer).Balance(0L).build();
        customer.setWallet(build);
        customer.setRegisterDate(LocalDate.now());
        walletService.saveOrUpdate(build);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> searchFromCustomer(ExpertFilter expertFilter) {
        Specification<Customer> spec = CustomerSpec.filterBy(expertFilter);
       return customerRepository.findAll(spec);
    }




}
