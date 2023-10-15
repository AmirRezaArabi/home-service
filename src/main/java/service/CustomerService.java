package service;


import domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CustomerService {

    Customer saveOrUpdate (Customer customer);

    String delete (Customer customer);
    Optional<Customer> findById (Long id);
    Optional<Customer> findByUserName (String userName);

    Optional<Customer> chngePassword (String oldPassword,String userName,String newPassword );

    List<Customer> findAll();



}
