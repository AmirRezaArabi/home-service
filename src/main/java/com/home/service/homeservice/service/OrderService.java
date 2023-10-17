package com.home.service.homeservice.service;

import com.home.service.homeservice.domain.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface OrderService {

    Order saveOrUpdate(Order order);


    String delete(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();
}
