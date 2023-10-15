package service;


import domain.CustomerRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CustomerRequestService {

    CustomerRequest saveOrUpdate (CustomerRequest customerRequest);
    String delete (CustomerRequest customerRequest);
    Optional<CustomerRequest> findById (Long id);
    List<CustomerRequest> findAll();
}
