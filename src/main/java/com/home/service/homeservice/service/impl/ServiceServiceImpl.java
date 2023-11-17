package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.exception.InvalidServiceNameExctetion;
import lombok.RequiredArgsConstructor;
import com.home.service.homeservice.repository.ServiceRepository;
import com.home.service.homeservice.service.ServiceService;

import java.util.List;


@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    @Override
    public Service saveOrUpdate(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public String delete(Service service) {
        serviceRepository.delete(service);
        return service.getName();
    }

    @Override
    public void deleteById(Long id) {
         serviceRepository.deleteById(id);
    }

    @Override
    public Service findById(Long id) {
        return serviceRepository.findById(id).orElseThrow(()->new IdIsNotExist("the id is not found"));
    }

    @Override
    public Service findByName(String name) {
        return serviceRepository.findByName(name).orElseThrow(()->new InvalidServiceNameExctetion("the service name not found"));
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }
}
