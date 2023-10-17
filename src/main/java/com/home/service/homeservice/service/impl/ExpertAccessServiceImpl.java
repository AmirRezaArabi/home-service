package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Suggestion;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.exception.TheInputTimeIsNotValid;
import com.home.service.homeservice.exception.TheSuggestedPriceIsLowerThanBasePriceException;
import com.home.service.homeservice.service.CustomerRequestService;
import com.home.service.homeservice.service.ExpertAccessService;
import com.home.service.homeservice.service.SuggestionService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class ExpertAccessServiceImpl implements ExpertAccessService {

    private final CustomerRequestService customerRequestService;
    private final SuggestionService suggestionService;


    @Override
    public boolean sendSuggestionByExpert(Expert expert, Long customerRequestId
            , Long suggestionPrice, LocalDate startWorkDay, int duration) {
        if (customerRequestService.findById(customerRequestId).isEmpty())
            throw new IdIsNotExist("customer Request Id is not exist");
        CustomerRequest customerRequest = customerRequestService.findById(customerRequestId).get();
        if (startWorkDay.isBefore(LocalDate.now()) || startWorkDay.isBefore(customerRequest.getStartDay()))
            throw new TheInputTimeIsNotValid("the input time is not valid");
        if (suggestionPrice < customerRequest.getSubService().getBasePrice())
            throw new TheSuggestedPriceIsLowerThanBasePriceException("The SuggestedPriceIsLowerThanBasePriceExveption");

        boolean isSave = suggestionService.saveOrUpdate(Suggestion.builder().expert(expert).customerRequest(customerRequest)
                .startWorkDay(startWorkDay).suggestionPrice(suggestionPrice).duration(duration)
                .suggestionDate(LocalDate.now()).build()) != null;
        if (isSave)
            customerRequest.setRequest_status(REQUEST_STATUS.WAITING_CHOOSE_EXPERT);
        boolean isUpdate = customerRequestService.saveOrUpdate(customerRequest)!=null;
        return(isSave && isUpdate);
    }


    @Override
    public List<CustomerRequest> showCustomRequest(Expert expert) {
        return customerRequestService.findAll().stream().
                filter(crs -> expert.getSubServices().contains(crs.getSubService()))
                .filter(crs -> crs.getRequest_status().equals(REQUEST_STATUS.WAITING_FOR_SUGGESTION)
                        || crs.getRequest_status().equals(REQUEST_STATUS.WAITING_CHOOSE_EXPERT)).toList();

    }
}
