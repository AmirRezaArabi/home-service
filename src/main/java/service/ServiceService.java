package service;

import domain.Service;


import java.util.List;
import java.util.Optional;
@org.springframework.stereotype.Service
public interface ServiceService {

    Service saveOrUpdate(Service service);

    String delete(Service service);

    Optional<Service> findById(Long id);
    Optional<Service> findByName (String name);


    List<Service> findAll();

}
