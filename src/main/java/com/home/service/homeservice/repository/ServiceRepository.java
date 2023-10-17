package com.home.service.homeservice.repository;


import com.home.service.homeservice.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {


    Optional<Service> findByName(String name);
}
