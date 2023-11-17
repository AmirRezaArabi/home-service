package com.home.service.homeservice.service;

import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Order;
import com.home.service.homeservice.domain.Suggestion;

import java.time.LocalDate;
import java.util.List;

public interface ExpertAccessService {



    Suggestion sendSuggestionByExpert (Expert expert, Long customerRequestId, Long suggestionPrice
            , LocalDate startWorkDay , int duration);


    List<CustomerRequest> showCustomRequest(Long expertId);

    List<Order> findAllByExpertUsername();



}
