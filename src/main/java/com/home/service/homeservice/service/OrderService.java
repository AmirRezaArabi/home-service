package com.home.service.homeservice.service;

import com.home.service.homeservice.domain.Order;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.dto.request.OrderFilterReqDTO;
import com.home.service.homeservice.filter.OrderFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    Order saveOrUpdate(Order order);


    String delete(Order order);

    Order findById(Long id);

    List<Order> findAll();

    List<Order> searchFromOrders(OrderFilterReqDTO orderFilterReqDTO);

    List<Order> findAllByRequest_status(REQUEST_STATUS request_status);

    List<Order> findOrdersForPay();

    List<Order> findAllByExpertUsername(String username);

}
