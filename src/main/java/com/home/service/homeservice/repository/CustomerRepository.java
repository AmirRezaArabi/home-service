package com.home.service.homeservice.repository;


import com.home.service.homeservice.domain.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface CustomerRepository extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByUsername(String username);

    List<Customer> findAll(@Nullable Specification<Customer> spec);

    Optional<Customer> findByEmailAddress(String emailAddress);


}
