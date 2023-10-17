package com.home.service.homeservice.service;

import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.Expert;

import java.time.LocalDate;
import java.util.List;

public interface ExpertAccessService {



    boolean sendSuggestionByExpert (Expert expert, Long customerRequestId, Long suggestionPrice
            , LocalDate startWorkDay ,int duration);


    List<CustomerRequest> showCustomRequest(Expert expert);
}
