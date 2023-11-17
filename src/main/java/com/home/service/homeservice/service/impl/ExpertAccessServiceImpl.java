package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Order;
import com.home.service.homeservice.domain.Suggestion;
import com.home.service.homeservice.domain.enums.EXPERT_STATUS;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.exception.TheInputTimeIsNotValid;
import com.home.service.homeservice.exception.TheNotConfirmedExpertException;
import com.home.service.homeservice.exception.TheSuggestedPriceIsLowerThanBasePriceException;
import com.home.service.homeservice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpertAccessServiceImpl implements ExpertAccessService {
    private final CustomerRequestService customerRequestService;
    private final SuggestionService suggestionService;

    private final OrderService orderService;

    private final ExpertService expertService;

    @Override
    public Suggestion sendSuggestionByExpert(Expert expert, Long customerRequestId
            , Long suggestionPrice, LocalDate startWorkDay, int duration) {
        if (!expert.getExpert_status().equals(EXPERT_STATUS.CONFIRMED))
            throw new TheNotConfirmedExpertException("your account is not confirmed");
        CustomerRequest customerRequest = customerRequestService.findById(customerRequestId);
        if (startWorkDay.isBefore(LocalDate.now()) || startWorkDay.isBefore(customerRequest.getStartDay()))
            throw new TheInputTimeIsNotValid("the input time is not valid");
        if (suggestionPrice < customerRequest.getSubService().getBasePrice())
            throw new TheSuggestedPriceIsLowerThanBasePriceException("The Suggested Price Is Lower Than Base Price");

        Suggestion suggestion = suggestionService.saveOrUpdate(Suggestion.builder().expert(expert).customerRequest(customerRequest)
                .startWorkDay(startWorkDay).suggestionPrice(suggestionPrice).duration(duration)
                .suggestionDate(LocalDate.now()).build());
        customerRequest.setStatus(REQUEST_STATUS.WAITING_CHOOSE_EXPERT);
        customerRequestService.saveOrUpdate(customerRequest);
        return suggestion;
    }

    @Override
    public List<CustomerRequest> showCustomRequest(Long expertId) {
        Expert expert = expertService.findById(expertId);
        if (!expert.getExpert_status().equals(EXPERT_STATUS.CONFIRMED))
            throw new TheNotConfirmedExpertException("your account is not confirmed");
        return customerRequestService.findAll().stream().
                filter(crs -> expert.getSubServices().contains(crs.getSubService()))
                .filter(crs -> crs.getStatus().equals(REQUEST_STATUS.WAITING_FOR_SUGGESTION)
                        || crs.getStatus().equals(REQUEST_STATUS.WAITING_CHOOSE_EXPERT)).toList();

    }

    @Override
    public List<Order> findAllByExpertUsername() {
        return orderService.findAllByExpertUsername(SecurityContextHolder.getContext().getAuthentication().getName()) ;
    }
}
