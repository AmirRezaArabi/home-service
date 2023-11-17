package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.*;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.dto.request.OrderFilterReqDTO;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.filter.OrderFilter;
import com.home.service.homeservice.repository.OrderRepository;
import com.home.service.homeservice.service.*;
import com.home.service.homeservice.utility.OrderSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ExpertService expertService;
    private final CustomerService customerService;
    private final SubServiceService subServiceService;
    private final ServiceService serviceService;

    @Override
    public Order saveOrUpdate(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public String delete(Order order) {
        orderRepository.delete(order);
        try {
            return order.getCustomer().toString();
        } catch (Exception e) {
            return "deleted";
        }
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IdIsNotExist("the id not found"));
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> searchFromOrders(OrderFilterReqDTO orderFilterReqDTO) {
        Customer customer = null;
        Expert expert = null;
        LocalDate start = null;
        LocalDate end = null;
        Service service = null;
        SubService subService = null;
        REQUEST_STATUS request_status = null;

        if (orderFilterReqDTO.getCustomerUsername() != null)
            customer = customerService.findByUsername(orderFilterReqDTO.getCustomerUsername());
        if (orderFilterReqDTO.getExpertUsername() != null)
            expert = expertService.findByUserName(orderFilterReqDTO.getExpertUsername());
        if (orderFilterReqDTO.getStartOrderTime() != null)
            start = orderFilterReqDTO.getStartOrderTime().toLocalDateTime().toLocalDate();
        if (orderFilterReqDTO.getEndOrderTime() != null)
            end = orderFilterReqDTO.getEndOrderTime().toLocalDateTime().toLocalDate();
        if (orderFilterReqDTO.getRequest_status() != null)
            request_status = REQUEST_STATUS.valueOf(orderFilterReqDTO.getRequest_status());
        if (orderFilterReqDTO.getServiceName() != null)
            service = serviceService.findByName(orderFilterReqDTO.getServiceName());
        if (orderFilterReqDTO.getSubServiceName() != null)
            subService = subServiceService.findByName(orderFilterReqDTO.getSubServiceName());
        OrderFilter orderFilter = new OrderFilter(
                customer, expert, start, end,request_status,service,subService);
        Specification<Order> spec = OrderSpec.filterBy(orderFilter);
        return orderRepository.findAll(spec);
    }

    @Override
    public List<Order> findAllByRequest_status(REQUEST_STATUS request_status) {
        return orderRepository.findAllByStatus(request_status);
    }

    @Override
    public List<Order> findOrdersForPay() {
        return orderRepository.findAllByStatusAndCustomerUsername(REQUEST_STATUS.DONE, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public List<Order> findAllByExpertUsername(String username) {
        return orderRepository.findAllByExpertUsername(username);
    }
}
