package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.repository.CustomerRepository;
import com.home.service.homeservice.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
class CustomerServiceImplTest {

    Customer customer;
    @MockBean
    CustomerRepository customerRepository ;

    @Autowired
    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customer = Customer.builder().name("test").emailAddress("test@gmail.com").userName("test1234")
                .password("3211test").build();
    }


    @Test
    void saveOrUpdate() {
        final var customerToSave = Customer.builder().name("amir").emailAddress("fsfsf@gmail.com").userName("asdf1234")
                .password("3211asdf").build();
        when(customerRepository.save(customerToSave)).thenReturn(customerToSave);
        final var response = customerService.saveOrUpdate(customerToSave);
        //assertThat(response).usingRecursiveComparison().isEqualTo(customerToSave);
        assertEquals(response,customerToSave);
        assertNotNull(response);
        verify(customerRepository,times(1)).save(any(Customer.class));
        verifyNoMoreInteractions(customerRepository);

    }


    @Test
    void delete() {
        doNothing().when(customerRepository).delete(any());

        customerService.delete(new Customer());
        verify(customerRepository, times(1)).delete(any());
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void findById() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        final var findCustomer = customerService.findById(anyLong());
        assertFalse(findCustomer.isEmpty());
        assertEquals(findCustomer.get(),customer);
        verify(customerRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void findByName() {
        when(customerRepository.findByUserName(anyString())).thenReturn(Optional.of(customer));
        final var findCustomer = customerService.findByUserName(anyString());
        assertFalse(findCustomer.isEmpty());
        assertEquals(findCustomer.get(),customer);
        verify(customerRepository, times(1)).findByUserName(anyString());
        verifyNoMoreInteractions(customerRepository);
    }


    @Test
    void findAll() {
        when(customerRepository.findAll()).thenReturn(List.of(new Customer(), new Customer()));
        assertEquals(customerService.findAll().size(),2);
        verify(customerRepository, times(1)).findAll();
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void findServiceBuCustomerName() {
        when(customerRepository.findByUserName(anyString())).thenReturn(Optional.of(customer));
        final var findService = customerService.findByUserName(anyString());
        assertFalse(findService.isEmpty());
        assertEquals(findService.get(),customer);
        verify(customerRepository, times(1)).findByUserName(anyString());
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void changePassword() {
        //todo: complete
    }

 
}