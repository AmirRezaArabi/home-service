package com.home.service.homeservice.service.impl;


import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.exception.InvalidServiceNameExctetion;
import com.home.service.homeservice.service.SubServiceService;
import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.domain.SubService;
import lombok.RequiredArgsConstructor;
import com.home.service.homeservice.repository.SubServiceRepository;


import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service

public class SubServiceServiceImpl implements SubServiceService {
    private final SubServiceRepository subServiceRepository;

    @Override
    public SubService saveOrUpdate(SubService subService) {
        return subServiceRepository.save(subService);
    }

    @Override
    public String delete(SubService subService) {
        subServiceRepository.delete(subService);
        return subService.getName();
    }

    @Override
    public void deleteById(Long id) {
        subServiceRepository.deleteById(id);
    }

    @Override
    public SubService findById(Long id) {
        return subServiceRepository.findById(id).orElseThrow(()->new IdIsNotExist("the id is not found"));
    }

    @Override
    public SubService findByName(String name) {
        return subServiceRepository.findSubServiceByName(name).orElseThrow(()->new InvalidServiceNameExctetion("the sub service name not found"));
    }

    @Override
    public List<SubService> findAll() {
        return subServiceRepository.findAll();
    }

    @Override
    public Service findServiceBySubServiceName(String name) {
         return subServiceRepository.findServiceBuSubServiceName(name).orElseThrow(()->new InvalidServiceNameExctetion("the service name not found"));
    }
}
