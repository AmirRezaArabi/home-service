package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Order;
import lombok.RequiredArgsConstructor;
import com.home.service.homeservice.repository.OrderRepository;
import com.home.service.homeservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.home.service.homeservice.validation.EntityValidator.isValid;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order saveOrUpdate(Order order) {
        if (!isValid(order))
            return null;
        return orderRepository.save(order);
    }

    @Override
    public String delete(Order order) {
        orderRepository.delete(order);
        try {
            return order.getCustomer().toString();
        }
        catch (Exception e)
        {
            return "deleted";
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
