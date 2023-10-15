package service;


import domain.Service;
import domain.SubService;

import java.util.List;
import java.util.Optional;
@org.springframework.stereotype.Service
public interface SubServiceService {

    SubService saveOrUpdate(SubService subService);

    String delete(SubService subService);

    Optional<SubService> findById(Long id);
    Optional<SubService> findByName(String name);

    List<SubService> findAll();
    Optional<Service> findServiceBuSubServiceName(String name);

}
