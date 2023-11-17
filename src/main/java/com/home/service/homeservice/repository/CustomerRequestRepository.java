package com.home.service.homeservice.repository;

import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRequestRepository extends JpaRepository<CustomerRequest,Long> {

    List<CustomerRequest> findAllByStatus(REQUEST_STATUS request_status);
}
