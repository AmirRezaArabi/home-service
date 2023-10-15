package service.impl;

import domain.Service;
import lombok.RequiredArgsConstructor;
import repository.ServiceRepository;
import service.ServiceService;

import java.util.List;
import java.util.Optional;

import static validation.EntityValidator.isValid;

@RequiredArgsConstructor
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
