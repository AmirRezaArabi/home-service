package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.*;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.service.CustomerRequestService;
import com.home.service.homeservice.service.ExpertAccessService;
import com.home.service.homeservice.service.SuggestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ExpertAccessServiceTest {

    @MockBean
    CustomerRequestService customerRequestService;
    @MockBean
    SuggestionService suggestionService;

    @Autowired
    ExpertAccessService expertAccessService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void sendSuggestionByExpert() {
        Expert expert = Expert.builder().name("test").comments(new ArrayList<>()).emailAddress("test@gmail.com").userName("test1234")
                .password("3211test").score(5).build();
        Service service1 = Service.builder().id(1L).name("test").build();
        SubService subService = SubService.builder().service(service1).expert(new ArrayList<>()).basePrice(1000L).build();
        CustomerRequest customerRequest = CustomerRequest.builder().startDay(LocalDate.of(2024,1,10)).subService(subService).build();
        Suggestion suggestion = Suggestion.builder().expert(expert).customerRequest(customerRequest)
                .startWorkDay(LocalDate.of(2024,12,2)).suggestionPrice(6000L).duration(5)
                .suggestionDate(LocalDate.now()).build();
        when(customerRequestService.findById(anyLong())).thenReturn(Optional.ofNullable(customerRequest));
        when(suggestionService.saveOrUpdate(any())).thenReturn(suggestion);

        expertAccessService.sendSuggestionByExpert(expert,1L,60000L,LocalDate.of(2024,2,2),5);
        assert customerRequest != null;
        assertEquals(customerRequest.getRequest_status(), REQUEST_STATUS.WAITING_CHOOSE_EXPERT);




    }


    @Test
    void showCustomRequest() {

        Service service1 = Service.builder().id(1L).name("test").build();
        SubService subService = SubService.builder().service(service1).expert(new ArrayList<>()).basePrice(1000L).build();
        Expert expert = Expert.builder().name("test").comments(new ArrayList<>()).emailAddress("test@gmail.com").userName("test1234")
                .password("3211test").subServices(new ArrayList<>(List.of(subService))).score(5).build();
        CustomerRequest customerRequest1 = CustomerRequest.builder().subService(subService).request_status(REQUEST_STATUS.STARTED).startDay(LocalDate.of(2024,1,10)).build();
        CustomerRequest customerRequest3 = CustomerRequest.builder().subService(subService).request_status(REQUEST_STATUS.WAITING_CHOOSE_EXPERT).startDay(LocalDate.of(2024,1,10)).build();
        CustomerRequest customerRequest2 = CustomerRequest.builder().request_status(REQUEST_STATUS.DONE).startDay(LocalDate.of(2024,1,10)).build();
        CustomerRequest customerRequest4 = CustomerRequest.builder().subService(subService).request_status(REQUEST_STATUS.WAITING_FOR_SUGGESTION).startDay(LocalDate.of(2024,1,10)).build();
        CustomerRequest customerRequest5 = CustomerRequest.builder().request_status(REQUEST_STATUS.WAITING_COMING_EXPERT).startDay(LocalDate.of(2024,1,10)).build();
        List<CustomerRequest> list = new ArrayList<>(List.of(customerRequest1,customerRequest2
        ,customerRequest3,customerRequest4,customerRequest5
        ));
        when(customerRequestService.findAll()).thenReturn(list);
        expertAccessService.showCustomRequest(expert.getId())
                .stream()
                .forEach(s-> System.out.println(s+"*******************************"));

    }
}