package com.home.service.homeservice.repository;

import com.home.service.homeservice.domain.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRequestRepository extends JpaRepository<CustomerRequest,Long> {

}
