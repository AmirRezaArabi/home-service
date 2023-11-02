package com.home.service.homeservice.service;


import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.dto.response.CustomerResponseDTO;
import com.home.service.homeservice.utility.CustomerFilter;
import com.home.service.homeservice.utility.ExpertFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CustomerService {

    Customer saveOrUpdate (Customer customer);

    String delete (Customer customer);
    void deleteById(Long id);
    Optional<Customer> findById (Long id);
    Optional<Customer> findByUserName (String userName);

    Optional<Customer> changePassword(String oldPassword, String userName, String newPassword );

    List<Customer> findAll();
    Customer signIn(String userName,String password) ;
    Customer signUp(Customer customer);
    List<Customer> searchFromCustomer(ExpertFilter expertFilter);






}
