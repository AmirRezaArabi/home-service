package com.home.service.homeservice.service;


import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.domain.SubService;
import com.home.service.homeservice.domain.base.User;
import com.home.service.homeservice.filter.ExpertFilter;

import java.util.List;

@org.springframework.stereotype.Service
public interface AdminAccessService {

    Service addService(String serviceName) ;

    SubService addSubService(String serviceName, String subServiceName, String description, Long basePrice );

    SubService updatePriceOrDescription(String subServiceName, String description, Long basePrice);

    Expert addExpertToSystem(String expertUserName, String subService);

    Expert removeExpertToSystem(String expertUserName,String subService);

    Expert confirmExpert(String expertUserName);

    List<User> searchFromCustomer(ExpertFilter expertFilter);
}
