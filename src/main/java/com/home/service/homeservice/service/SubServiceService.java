package com.home.service.homeservice.service;


import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.domain.SubService;

import java.util.List;

@org.springframework.stereotype.Service
public interface SubServiceService {

    SubService saveOrUpdate(SubService subService);

    String delete(SubService subService);
    void deleteById(Long id );

    SubService findById(Long id);
    SubService findByName(String name);

    List<SubService> findAll();
    Service findServiceBySubServiceName(String name);

}
