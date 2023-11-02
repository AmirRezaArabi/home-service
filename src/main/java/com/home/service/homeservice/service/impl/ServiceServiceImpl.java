package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Service;
import lombok.RequiredArgsConstructor;
import com.home.service.homeservice.repository.ServiceRepository;
import com.home.service.homeservice.service.ServiceService;

import java.util.List;
import java.util.Optional;

import static com.home.service.homeservice.validation.EntityValidator.isValid;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    @Override
    public Service saveOrUpdate(Service service) {
        if (!isValid(service))
            return null;
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
    public Optional<Service> findById(Long id) {
        return serviceRepository.findById(id);
    }

    @Override
    public Optional<Service> findByName(String name) {
        return serviceRepository.findByName(name);
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }
}
