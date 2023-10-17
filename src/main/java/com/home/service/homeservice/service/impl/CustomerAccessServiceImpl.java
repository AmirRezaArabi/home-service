package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.*;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.exception.TheInputTimeIsNotValid;
import com.home.service.homeservice.exception.TheSuggestedPriceIsLowerThanBasePriceException;
import com.home.service.homeservice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerAccessServiceImpl implements CustomerAccessService {
    private final CustomerService customerService;
    private final ExpertService expertService;
    private final SubServiceService subServiceService;
    private final CustomerRequestService customerRequestService;
    private final OrderService orderService;

    private final SuggestionService suggestionService;

    @Override
    public boolean placeAnRequest(Long subServiceId, String customerUserName, LocalDate time
            , Long suggestionPrice, String description, String address) {

        Customer customer = customerService.findByUserName(customerUserName).get();
        SubService subService = subServiceService.findById(subServiceId).get();
        if (suggestionPrice < subService.getBasePrice())
            throw new TheSuggestedPriceIsLowerThanBasePriceException("The suggested price is lower than the base price");
        if (time.isBefore(LocalDate.now()))
            throw new TheInputTimeIsNotValid("the input time is before now");
        CustomerRequest customerRequest = CustomerRequest.builder().subService(subService)
                .customer(customer).startDay(time).suggestionPrice(suggestionPrice)
                .description(description).address(address).request_status(REQUEST_STATUS.WAITING_FOR_SUGGESTION)
                .build();
        return customerRequestService.saveOrUpdate(customerRequest) != null;
    }

    @Override
    public boolean setScoreByCustomer(Long orderId, int score) {
        if (orderService.findById(orderId).isEmpty())
            throw new IdIsNotExist("id is not exist");
        Order order = orderService.findById(orderId).get();
        order.setScore(score);
        Expert expert = order.getExpert();
        expert.setScore(expert.getScore() + score);
        expertService.saveOrUpdate(expert);
        return orderService.saveOrUpdate(order) != null;
    }

    @Override
    public boolean setCommentByCustomer(Long orderId, String comment) {
        if (orderService.findById(orderId).isEmpty())
            throw new IdIsNotExist("id is not exist");
        Order order = orderService.findById(orderId).get();
        order.setComment(comment);
        Expert expert = order.getExpert();
        expert.getComments().add(comment);
        expertService.saveOrUpdate(expert);
        return orderService.saveOrUpdate(order) != null;
    }

    @Override
    public List<Suggestion> showSuggestion(Long customerRequestId) {
        if (customerRequestService.findById(customerRequestId).isEmpty())
            throw new IdIsNotExist("id is not exist ");
        return suggestionService.findAll()
                .stream()
                .filter(s -> s.getCustomerRequest()
                        .equals(customerRequestService.findById(customerRequestId).get()))
                .toList();
    }

    @Override
    public boolean chooseSuggestion(Long suggestionId) {
        if (suggestionService.findById(suggestionId).isEmpty())
            throw new IdIsNotExist("id is not exist");
        Suggestion suggestion = suggestionService.findById(suggestionId).get();
        CustomerRequest customerRequest = suggestion.getCustomerRequest();
        customerRequest.setRequest_status(REQUEST_STATUS.WAITING_COMING_EXPERT);
        Order order = Order.builder().subService(customerRequest.getSubService())
                .customer(customerRequest.getCustomer())
                .expert(suggestion.getExpert())
                .request_status(REQUEST_STATUS.WAITING_COMING_EXPERT)
                .Price(suggestion.getSuggestionPrice())
                .description(customerRequest.getDescription())
                .startDay(suggestion.getStartWorkDay())
                .duration(suggestion.getDuration()).build();
        boolean isUpdate = customerRequestService.saveOrUpdate(customerRequest) != null;
        boolean isSave = orderService.saveOrUpdate(order) != null;
        return (isUpdate && isSave);
    }

    @Override
    public boolean changeRequestStatusToStarted(Long orderId) {
        if (orderService.findById(orderId).isEmpty())
            throw new IdIsNotExist("id is not exist");
        Order order = orderService.findById(orderId).get();
        if (LocalDate.now().isBefore(order.getStartDay()))
            throw new TheInputTimeIsNotValid("the input time is before start work day");
        order.setRequest_status(REQUEST_STATUS.STARTED);
        return orderService.saveOrUpdate(order) != null;
    }

    @Override
    public boolean changeRequestStatusToDone(Long orderId) {
        if (orderService.findById(orderId).isEmpty())
            throw new IdIsNotExist("id is not exist");
        Order order = orderService.findById(orderId).get();
        order.setRequest_status(REQUEST_STATUS.DONE);
        return orderService.saveOrUpdate(order) != null;
    }


}
