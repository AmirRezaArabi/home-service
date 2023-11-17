package com.home.service.homeservice.repository;
import com.home.service.homeservice.domain.Order;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
    List<Order> findAll(@Nullable Specification<Order> spec);


    List<Order> findAllByStatus(REQUEST_STATUS request_status);

    List<Order> findAllByStatusAndCustomerUsername(REQUEST_STATUS request_status,String username);

    List<Order> findAllByExpertUsername(String username);

}