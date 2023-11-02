package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.*;
import com.home.service.homeservice.domain.enums.EXPERT_STATUS;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.exception.TheInputTimeIsNotValid;
import com.home.service.homeservice.exception.TheSuggestedPriceIsLowerThanBasePriceException;
import com.home.service.homeservice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
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
    public CustomerRequest placeAnRequest(Long subServiceId, String customerUserName, LocalDate time
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
        return customerRequestService.saveOrUpdate(customerRequest);
    }

    @Override
    public boolean setScoreByCustomer(Long orderId, int score) {
        if (score > 5) throw new IllegalArgumentException("input score must between 1 to 5");
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
    public Order chooseSuggestion(Long suggestionId) {
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
        customerRequestService.saveOrUpdate(customerRequest);
        return orderService.saveOrUpdate(order);
    }

    @Override
    public Order changeRequestStatusToStarted(Long orderId) {
        if (orderService.findById(orderId).isEmpty())
            throw new IdIsNotExist("id is not exist");
        Order order = orderService.findById(orderId).get();
        //todo : check
        if (LocalDate.now().isAfter(order.getStartDay()))
            throw new TheInputTimeIsNotValid("the input time is before start work day");
        order.setRequest_status(REQUEST_STATUS.STARTED);
        return orderService.saveOrUpdate(order);
    }

    @Override
    public Order
    changeRequestStatusToDone(Long orderId) {
        if (orderService.findById(orderId).isEmpty())
            throw new IdIsNotExist("id is not exist");
        Order order = orderService.findById(orderId).get();
        Expert expert = order.getExpert();
        order.setRequest_status(REQUEST_STATUS.DONE);
        int minesScore = Period.between(order.getStartDay().plusDays(order.getDuration()), LocalDate.now()).getDays();
        expert.setScore(expert.getScore() - minesScore);
        if (expert.getScore() < 0)
            expert.setExpert_status(EXPERT_STATUS.DEACTIVATE);
        expertService.saveOrUpdate(expert);
        return orderService.saveOrUpdate(order);
    }


}
