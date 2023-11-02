package com.home.service.homeservice.service;

import com.home.service.homeservice.domain.Service;


import java.util.List;
import java.util.Optional;
@org.springframework.stereotype.Service
public interface ServiceService {

    Service saveOrUpdate(Service service);

    String delete(Service service);
    void deleteById(Long id);

    Optional<Service> findById(Long id);
    Optional<Service> findByName (String name);


    List<Service> findAll();

}
