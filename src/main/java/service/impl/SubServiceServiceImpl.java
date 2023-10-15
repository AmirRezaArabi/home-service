package service.impl;


import domain.Service;
import domain.SubService;
import lombok.RequiredArgsConstructor;
import repository.SubServiceRepository;
import service.SubServiceService;


import java.util.List;
import java.util.Optional;

import static validation.EntityValidator.isValid;

@RequiredArgsConstructor
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
