package com.home.service.homeservice.service.impl;


import com.home.service.homeservice.service.SubServiceService;
import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.domain.SubService;
import lombok.RequiredArgsConstructor;
import com.home.service.homeservice.repository.SubServiceRepository;


import java.util.List;
import java.util.Optional;

import static com.home.service.homeservice.validation.EntityValidator.isValid;

@RequiredArgsConstructor
@org.springframework.stereotype.Service

public class SubServiceServiceImpl implements SubServiceService {
    private final SubServiceRepository subServiceRepository;

    @Override
    public SubService saveOrUpdate(SubService subService) {
        if (!isValid(subService))
            return null;
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
    public Optional<SubService> findById(Long id) {
        return subServiceRepository.findById(id);
    }

    @Override
    public Optional<SubService> findByName(String name) {
        return subServiceRepository.findSubServiceByName(name);
    }

    @Override
    public List<SubService> findAll() {
        return subServiceRepository.findAll();
    }

    @Override
    public Optional<Service> findServiceBuSubServiceName(String name) {
         return subServiceRepository.findServiceBuSubServiceName(name);
    }
}
