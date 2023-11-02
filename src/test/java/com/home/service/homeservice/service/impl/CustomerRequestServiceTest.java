package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.SubService;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.repository.CustomerRequestRepository;
import com.home.service.homeservice.service.CustomerRequestService;
import com.home.service.homeservice.validation.EntityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerRequestServiceTest {


    CustomerRequest customerRequest;


    Customer customer ;
    SubService subService;
    @MockBean
    CustomerRequestRepository customerRequestRepository ;

    @Autowired
    CustomerRequestService customerRequestService;

    @BeforeEach
    void setUp() {
        customer = Customer.builder().id(1L).name("test").emailAddress("test@gmail.com").userName("test1234")
                .password("3211test").build();
        CustomerRequest customerRequest  = CustomerRequest.builder().id(2L).request_status(REQUEST_STATUS.WAITING_CHOOSE_EXPERT)
                .startDay(LocalDate.now()).subService(new SubService()).address("test address").description("test test ").customer(customer).build();
         subService = SubService.builder().basePrice(123L).build();


    }


    @Test
    void saveOrUpdate() {
        final var customerRequestToSave = CustomerRequest.builder().id(1L).address("test address").description("test test ")
                .subService(subService).customer(customer).build();
        when(customerRequestRepository.save(customerRequestToSave)).thenReturn(customerRequestToSave);
        final var response = customerRequestService.saveOrUpdate(customerRequestToSave);
        //assertThat(response).usingRecursiveComparison().isEqualTo(customerRequestToSave);
        assertEquals(response,customerRequestToSave);
        assertNotNull(response);
        verify(customerRequestRepository,times(1)).save(any(CustomerRequest.class));
        verifyNoMoreInteractions(customerRequestRepository);

    }


    @Test
    void delete() {
        doNothing().when(customerRequestRepository).delete(any());

        customerRequestService.delete(customerRequest);
        verify(customerRequestRepository, times(1)).delete(customerRequest);
        verifyNoMoreInteractions(customerRequestRepository);
    }

    @Test
    void findById() {
        CustomerRequest customerRequestForFind  = CustomerRequest.builder().id(2L).request_status(REQUEST_STATUS.WAITING_CHOOSE_EXPERT)
                .startDay(LocalDate.now()).subService(new SubService()).address("test address").description("test test ").customer(customer).build();
        when(customerRequestRepository.findById(anyLong())).thenReturn(Optional.of(customerRequestForFind));
        final var findCustomerRequest = customerRequestService.findById(anyLong());
        assertFalse(findCustomerRequest.isEmpty());
        assertEquals(findCustomerRequest.get(),customerRequestForFind);
        verify(customerRequestRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(customerRequestRepository);
    }



    @Test
    void findAll() {
        when(customerRequestRepository.findAll()).thenReturn(List.of(new CustomerRequest(), new CustomerRequest()));
        assertEquals(customerRequestService.findAll().size(),2);
        verify(customerRequestRepository, times(1)).findAll();
        verifyNoMoreInteractions(customerRequestRepository);
    }
}