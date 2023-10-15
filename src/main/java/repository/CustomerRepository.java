package repository;


import domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Component
public interface CustomerRepository extends CrudRepository<Customer,Long> {

    Optional<Customer> findByUserName(String userName);


}
