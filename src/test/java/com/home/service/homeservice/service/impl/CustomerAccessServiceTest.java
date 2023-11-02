package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.*;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerAccessServiceTest {

    @MockBean
    ExpertService expertService;
    @MockBean
    SuggestionService suggestionService;
    @MockBean
    CustomerService customerService;
    @MockBean
    ServiceService serviceService;
    @MockBean
    SubServiceService subServiceService;
    @MockBean
    OrderService orderService;
    @MockBean
    CustomerRequestService customerRequestService;
    @Autowired
    CustomerAccessService customerAccessService;


    @BeforeEach
    void setUp() {
    }

    @Test
    @Rollback(value = false)
    void placeAnRequest() {
        Customer customer = Customer.builder().name("test").emailAddress("test@gmail.com").userName("test1234")
                .password("3211test").build();
        Service service1 = Service.builder().id(1L).name("test").build();
        SubService subService = SubService.builder().service(service1).expert(new ArrayList<>()).basePrice(1000L).build();
        when(customerService.findByUserName(anyString())).thenReturn(Optional.of(customer));
        when(subServiceService.findById(anyLong())).thenReturn(Optional.of(subService));
        when(customerRequestService.saveOrUpdate(any())).thenReturn(new CustomerRequest());
        Assertions.assertTrue(customerAccessService.placeAnRequest(1L,"hkj",
                LocalDate.of(2024,10,1),2000L,"test","test"));

    }

    @Test
    void setScoreByCustomer() {
        Expert expert = Expert.builder().name("test").emailAddress("test@gmail.com").userName("test1234")
                .password("3211test").score(5).build();
        Order order = Order.builder().expert(expert).description("test").startDay(LocalDate.of(2024,12,12))
                .Price(1000L).duration(5).build();
                when(orderService.findById(anyLong())).thenReturn(Optional.of(order));
                when(orderService.saveOrUpdate(any())).thenReturn(order);
        customerAccessService.setScoreByCustomer(5L,5);
        assertEquals(order.getScore(),5);
        assertEquals(order.getExpert().getScore(),10);
    }

    @Test
    void setCommentByCustomer() {
        Expert expert = Expert.builder().name("test").comments(new ArrayList<>()).emailAddress("test@gmail.com").userName("test1234")
                .password("3211test").score(5).build();
        Order order = Order.builder().expert(expert).description("test").startDay(LocalDate.of(2024,12,12))
                .Price(1000L).duration(5).build();
        when(orderService.findById(anyLong())).thenReturn(Optional.ofNullable(order));
        when(orderService.saveOrUpdate(any())).thenReturn(order);
        customerAccessService.setCommentByCustomer(1L,"test");
        assert order != null;
        assertEquals(order.getComment(),"test");
        assert order.getComment().contains("test");

    }

    @Test
    void showSuggestion() {
        Service service1 = Service.builder().id(1L).name("test").build();
        CustomerRequest customerRequest1 = CustomerRequest.builder().build();
        Customer customer = Customer.builder().name("test").emailAddress("test@gmail.com").userName("test1234")
                .password("3211test").build();
        SubService subService = SubService.builder().service(service1).expert(new ArrayList<>()).basePrice(1000L).build();
        Expert expert = Expert.builder().name("test").userName("test1234")
                .emailAddress("test@gmail.com").subServices(new ArrayList<>()).password("test1234").build();
        expert.getSubServices().add(subService);
        CustomerRequest customerRequest = CustomerRequest.builder().customer(customer).subService(subService)
                .address("test").description("test").suggestionPrice(1000L).startDay(LocalDate.now()).subService(subService).build();
        when(customerRequestService.findById(anyLong())).thenReturn(Optional.ofNullable(customerRequest));
        List<Suggestion> s = new ArrayList<>();
        when(suggestionService.findAll()).thenReturn(s);
        s.add(Suggestion.builder().customerRequest(customerRequest).build());
        s.add(Suggestion.builder().customerRequest(customerRequest1).build());
        customerAccessService.showSuggestion(1L).stream().forEach(r-> System.out.println(r.getCustomerRequest()+ "test test "));


    }

    @Test
    void chooseSuggestion() {
//        Service service1 = Service.builder().id(1L).name("test").build();
//        SubService subService = SubService.builder().service(service1).expert(new ArrayList<>()).basePrice(1000L).build();
//        Expert expert = Expert.builder().name("test").userName("test1234")
//                .emailAddress("test@gmail.com").subServices(new ArrayList<>()).password("test1234").build();
//        Customer customer = Customer.builder().name("test").emailAddress("test@gmail.com").userName("test1234")
//                .password("3211test").build();
//        CustomerRequestDTO customerRequest = CustomerRequestDTO.builder().customer(customer).subService(subService)
//                .address("test").description("test").suggestionPrice(1000L).startDay(LocalDate.now()).subService(subService).build();
//        Suggestion build = Suggestion.builder().customerRequest(customerRequest).build();
//        Order order = Order.builder().build();
        when(suggestionService.findById(anyLong())).thenReturn(Optional.of(Suggestion.builder().customerRequest(new CustomerRequest()).build()));
        when(customerRequestService.saveOrUpdate(any())).thenReturn(new CustomerRequest());
        when(orderService.saveOrUpdate(any())).thenReturn(new Order());
        customerAccessService.chooseSuggestion(1L);
        verify(suggestionService,times(2)).findById(anyLong());
      verify(orderService,times(1)).saveOrUpdate(any());
      verify(customerRequestService,times(1)).saveOrUpdate(any());}

    @Test
    void changeRequestStatusToStarted() {
        Expert expert = Expert.builder().name("test").comments(new ArrayList<>()).emailAddress("test@gmail.com").userName("test1234")
                .password("3211test").score(5).build();
        Order order = Order.builder().expert(expert).description("test").startDay(LocalDate.of(2030,12,12))
                .Price(1000L).duration(5).build();
        when(orderService.findById(anyLong())).thenReturn(Optional.ofNullable(order));
        when(orderService.saveOrUpdate(order)).thenReturn(order);
        customerAccessService.changeRequestStatusToStarted(1L);
        assert order != null;
        assertEquals(order.getRequest_status(), REQUEST_STATUS.STARTED);

    }

    @Test
    void changeRequestStatusToDone() {
        Expert expert = Expert.builder().name("test").comments(new ArrayList<>()).emailAddress("test@gmail.com").userName("test1234")
                .password("3211test").score(5).build();
        Order order = Order.builder().expert(expert).description("test").startDay(LocalDate.of(2023,12,12))
                .Price(1000L).duration(5).build();
        when(orderService.findById(anyLong())).thenReturn(Optional.ofNullable(order));
        when(orderService.saveOrUpdate(order)).thenReturn(order);
        customerAccessService.changeRequestStatusToDone(1L);
        assert order != null;
        assertEquals(order.getRequest_status(), REQUEST_STATUS.DONE);


    }
}