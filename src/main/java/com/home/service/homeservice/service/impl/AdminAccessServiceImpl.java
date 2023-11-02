package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.domain.SubService;
import com.home.service.homeservice.domain.base.User;
import com.home.service.homeservice.domain.enums.EXPERT_STATUS;
import com.home.service.homeservice.exception.InvalidServiceNameExctetion;
import com.home.service.homeservice.exception.TheInputInformationIsNotValidException;
import com.home.service.homeservice.service.*;
import com.home.service.homeservice.utility.ExpertFilter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class AdminAccessServiceImpl implements AdminAccessService {


    private final ExpertService expertService;

    private final CustomerService customerService;
    private  final ServiceService serviceService;
    private  final SubServiceService subServiceService;



    @Override
    public Service addService(String serviceName) {
        Service service = Service.builder().name(serviceName).build();
        return serviceService.saveOrUpdate(service) ;

    }

    @Override
    public SubService addSubService(String serviceName, String subServiceName, String description, Long basePrice) {
        if (serviceService.findByName(serviceName).isEmpty())
            throw new InvalidServiceNameExctetion("the input service name is not present");
        Service service = serviceService.findByName(serviceName).get();
        SubService subService = SubService.builder().service(service).name(subServiceName)
                .description(description).basePrice(basePrice).build();
        return subServiceService.saveOrUpdate(subService) ;
    }

    @Override
    public SubService updatePriceOrDescription(String SubServiceName, String description, Long basePrice) {
        if (subServiceService.findByName(SubServiceName).isEmpty())
            throw new InvalidServiceNameExctetion(" the input Sub service name is not valid");
        SubService SubService = subServiceService.findByName(SubServiceName).get();
        SubService.setBasePrice(basePrice);
        SubService.setDescription(description);
        return subServiceService.saveOrUpdate(SubService) ;
    }

    @Override
    public Expert addExpertToSystem(String expertUserName, String subServiceName) {
        if (expertService.findByUserName(expertUserName).isEmpty() || subServiceService.findByName(subServiceName).isEmpty())
            throw new TheInputInformationIsNotValidException("the input information is not correct ");
        Expert expert = expertService.findByUserName(expertUserName).get();
        SubService subService = subServiceService.findByName(subServiceName).get();
        expert.getSubServices().add(subService);
        subService.getExpert().add(expert);
        subServiceService.saveOrUpdate(subService);
        return ( expertService.saveOrUpdate(expert));

    }

    @Override
    public Expert removeExpertToSystem(String expertUserName, String subServiceName) {
        if (expertService.findByUserName(expertUserName).isEmpty() || subServiceService.findByName(subServiceName).isEmpty())
            throw new TheInputInformationIsNotValidException("the input information is not correct ");
        Expert expert = expertService.findByUserName(expertUserName).get();
        SubService subService = subServiceService.findByName(subServiceName).get();
        expert.getSubServices().remove(subService);
        subService.getExpert().remove(expert);
        subServiceService.saveOrUpdate(subService);
        return expertService.saveOrUpdate(expert);
    }

    @Override
    public Expert confirmExpert(String expertUserName) {
        if (expertService.findByUserName(expertUserName).isEmpty())
            throw new TheInputInformationIsNotValidException("the input information is not correct ");
        Expert expert = expertService.findByUserName(expertUserName).get();
        expert.setExpert_status(EXPERT_STATUS.CONFIRMED);
        return expertService.saveOrUpdate(expert) ;

    }

    @Override
    public List<User> searchFromCustomer(ExpertFilter expertFilter) {
        List<Expert> experts = expertService.searchFromExpert(expertFilter);
        List<Customer> customers = customerService.searchFromCustomer(expertFilter);
        List<User> users = new ArrayList<>();
        users.addAll(experts);
        users.addAll(customers);
         return users;
        }
    }

