package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.*;
import com.home.service.homeservice.domain.enums.EXPERT_STATUS;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.exception.BadStatusException;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.exception.TheInputTimeIsNotValid;
import com.home.service.homeservice.exception.TheSuggestedPriceIsLowerThanBasePriceException;
import com.home.service.homeservice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.home.service.homeservice.domain.enums.REQUEST_STATUS.WAITING_CHOOSE_EXPERT;
import static com.home.service.homeservice.domain.enums.REQUEST_STATUS.WAITING_FOR_SUGGESTION;

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

        Customer customer = customerService.findByUsername(customerUserName);
        SubService subService = subServiceService.findById(subServiceId);
        if (suggestionPrice < subService.getBasePrice())
            throw new TheSuggestedPriceIsLowerThanBasePriceException("The suggested price is lower than the base price");
        if (time.isBefore(LocalDate.now()))
            throw new TheInputTimeIsNotValid("the input time is before now");
        CustomerRequest customerRequest = CustomerRequest.builder().subService(subService)
                .customer(customer).startDay(time).suggestionPrice(suggestionPrice)
                .description(description).address(address).status(REQUEST_STATUS.WAITING_FOR_SUGGESTION)
                .build();
        return customerRequestService.saveOrUpdate(customerRequest);
    }

    @Override
    public Order setScoreByCustomer(Long orderId, int score) {
        if (score > 5) throw new IllegalArgumentException("input score must between 1 to 5");
        Order order = orderService.findById(orderId);
        if (order.getStatus() != REQUEST_STATUS.DONE) throw new IdIsNotExist("you order not done ");
        order.setScore(score);
        Expert expert = order.getExpert();
        expert.setScore(expert.getScore() + score);
        expertService.saveOrUpdate(expert);
        return orderService.saveOrUpdate(order);
    }

    @Override
    public Order setCommentByCustomer(Long orderId, String comment) {
        Order order = orderService.findById(orderId);
        if (order.getStatus() != REQUEST_STATUS.DONE)
            throw new IdIsNotExist("you order not done ");
        order.setComment(comment);
        Expert expert = order.getExpert();
        expert.getComments().add(comment);
        expertService.saveOrUpdate(expert);
        return orderService.saveOrUpdate(order);
    }

    @Override
    public List<Suggestion> showSuggestion(Long customerRequestId) {
        return suggestionService.findAll()
                .stream()
                .filter(s -> s.getCustomerRequest()
                        .equals(customerRequestService.findById(customerRequestId)))
                .toList();
    }

    @Override
    public Order chooseSuggestion(Long suggestionId) {
        Suggestion suggestion = suggestionService.findById(suggestionId);
        CustomerRequest customerRequest = suggestion.getCustomerRequest();
        customerRequest.setStatus(REQUEST_STATUS.WAITING_COMING_EXPERT);
        Order order = Order.builder().subService(customerRequest.getSubService())
                .customer(customerRequest.getCustomer())
                .expert(suggestion.getExpert())
                .status(REQUEST_STATUS.WAITING_COMING_EXPERT)
                .Price(suggestion.getSuggestionPrice())
                .description(customerRequest.getDescription())
                .startDay(suggestion.getStartWorkDay())
                .duration(suggestion.getDuration()).build();
        customerRequestService.saveOrUpdate(customerRequest);
        return orderService.saveOrUpdate(order);
    }

    @Override
    public Order changeRequestStatusToStarted(Long orderId) {
        Order order = orderService.findById(orderId);
        if (!order.getStatus().equals(REQUEST_STATUS.WAITING_COMING_EXPERT))
            throw new BadStatusException("your order not in wait for coming expert status");
        //todo : check for time is correct valid
        if (LocalDate.now().isBefore(order.getStartDay()))
            throw new TheInputTimeIsNotValid("the input time is before start work day");
        order.setStatus(REQUEST_STATUS.STARTED);
        return orderService.saveOrUpdate(order);
    }

    @Override
    public Order
    changeRequestStatusToDone(Long orderId) {
        Order order = orderService.findById(orderId);
        if (!order.getStatus().equals(REQUEST_STATUS.STARTED))
            throw new BadStatusException("your order not in start status");
        Expert expert = order.getExpert();
        order.setStatus(REQUEST_STATUS.DONE);
        int minesScore = Period.between(order.getStartDay().plusDays(order.getDuration()), LocalDate.now()).getDays();
        expert.setScore(expert.getScore() - minesScore);
        if (expert.getScore() < 0) {
            expert.setIsEnabled(false);
            expert.setExpert_status(EXPERT_STATUS.DEACTIVATE);
        }
        expertService.saveOrUpdate(expert);
        return orderService.saveOrUpdate(order);
    }

    @Override
    public List<?> findOrdersByRequestStatus(REQUEST_STATUS request_status) {
        if (request_status.equals(WAITING_FOR_SUGGESTION) || request_status.equals(WAITING_CHOOSE_EXPERT))
            return customerRequestService.findAllByRequest_status(request_status);
        else
            return orderService.findAllByRequest_status(request_status);

    }

    @Override
    public List<Order> findOrdersForPay() {
        return orderService.findOrdersForPay();
    }

    @Override
    public List<Suggestion> findAllByCustomerRequestOrderBySuggestionPriceAsc(Long customerRequestId) {
        return suggestionService.findAllByCustomerRequestOrderBySuggestionPriceAsc
                        (customerRequestService.findById(customerRequestId))
                .stream()
                .sorted(Comparator.comparing(o -> o.getExpert().getScore()))
                .collect(Collectors.toList());
    }
}
