package com.home.service.homeservice.repository;


import com.home.service.homeservice.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByUserName(String userName);


}
