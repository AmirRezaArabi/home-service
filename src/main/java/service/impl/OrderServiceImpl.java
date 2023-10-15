package service.impl;

import domain.Order;
import lombok.RequiredArgsConstructor;
import repository.OrderRepository;
import service.OrderService;

import java.util.List;
import java.util.Optional;

import static validation.EntityValidator.isValid;

@RequiredArgsConstructor
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
        return order.getCustomer().getUserName();
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
