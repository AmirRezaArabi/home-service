package com.home.service.homeservice.service;

import org.springframework.stereotype.Service;

@Service
public interface AdminAccessService {

    boolean addService(String serviceName) ;

    boolean addSubService(String serviceName, String subServiceName, String description, Long basePrice );

    boolean updatePriceOrDescription(String subServiceName, String description, Long basePrice);

    boolean addExpertToSystem(String expertUserName,String subService);

    boolean removeExpertToSystem(String expertUserName,String subService);

    boolean confirmExpert(String expertUserName);

}
