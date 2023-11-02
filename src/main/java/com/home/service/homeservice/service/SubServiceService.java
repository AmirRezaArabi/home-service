package com.home.service.homeservice.service;


import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.domain.SubService;

import java.util.List;
import java.util.Optional;
@org.springframework.stereotype.Service
public interface SubServiceService {

    SubService saveOrUpdate(SubService subService);

    String delete(SubService subService);
    void deleteById(Long id );

    Optional<SubService> findById(Long id);
    Optional<SubService> findByName(String name);

    List<SubService> findAll();
    Optional<Service> findServiceBuSubServiceName(String name);

}
