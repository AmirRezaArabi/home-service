package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.domain.enums.Role;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.exception.TheEmailNotFoundException;
import com.home.service.homeservice.exception.TheInputInformationIsNotValidException;
import com.home.service.homeservice.exception.UserNameOrPasswordDosNotExistException;
import com.home.service.homeservice.repository.CustomerRepository;
import com.home.service.homeservice.service.CustomerService;
import com.home.service.homeservice.service.WalletService;
import com.home.service.homeservice.utility.CustomerSpec;
import com.home.service.homeservice.filter.ExpertFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Streamable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service

public class CustomerServiceImpl implements CustomerService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    private final WalletService walletService;


    @Override
    public Customer saveOrUpdate(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public String delete(Customer customer) {
         customerRepository.delete(customer);
        return customer.getUsername();
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }


    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(()->new IdIsNotExist("the id not found"));
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("the username not found "));
    }

    @Override
    public Customer changePassword(String oldPassword, String userName, String newPassword) {
        Customer customer =  findByUsername(userName);
        if (!customer.getPassword().equals(oldPassword)) throw new TheInputInformationIsNotValidException("The Input Information Is Not Valid ");
        customer.setPassword(newPassword);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return Streamable.of(customerRepository.findAll()).toList();
    }

    @Override
    public Customer signIn(String userName, String password) {
        Customer customer = findByUsername(userName);
        if (!passwordEncoder.matches(password,customer.getPassword()))
            throw new UserNameOrPasswordDosNotExistException("the input Password Not correct");
        return customer;
    }


    @Override
    public Customer signUp(Customer customer) {
        Wallet build = Wallet.builder().user(customer).Balance(0L).build();
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole(Role.ROLE_CUSTOMER);
        customer.setWallet(build);
        customer.setRegisterDate(LocalDate.now());
        customer.setIsEnabled(false);
        walletService.saveOrUpdate(build);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> searchFromCustomer(ExpertFilter expertFilter) {
        Specification<Customer> spec = CustomerSpec.filterBy(expertFilter);
       return customerRepository.findAll(spec);
    }

    @Override
    public Customer findByEmailAddress(String emailAddress) {
        return customerRepository.findByEmailAddress(emailAddress).orElseThrow(()->new TheEmailNotFoundException("email address not found"));
    }


}
