package com.home.service.homeservice.service;


import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.filter.ExpertFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    Customer saveOrUpdate (Customer customer);

    String delete (Customer customer);
    void deleteById(Long id);
    Customer findById (Long id);
    Customer findByUsername(String username);

    Customer changePassword(String oldPassword, String userName, String newPassword );

    List<Customer> findAll();
    Customer signIn(String userName,String password) ;
    Customer signUp(Customer customer);
    List<Customer> searchFromCustomer(ExpertFilter expertFilter);

    Customer findByEmailAddress(String emailAddress);







}
