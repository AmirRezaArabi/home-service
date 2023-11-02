package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Order;
import com.home.service.homeservice.domain.SubService;
import com.home.service.homeservice.repository.OrderRepository;
import com.home.service.homeservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceImplTest {
    Order orderToSave ;
    @MockBean
    OrderRepository orderRepository ;
    
    @Autowired
    OrderService orderService;
    Expert expert;
    Customer customer;
    SubService subService;

    @BeforeEach
    void setUp() {
         orderToSave = Order.builder().id(1L).duration(5).description("test").Price(123L).build();
         expert = Expert.builder().build();
         customer = Customer.builder().build();
         subService = SubService.builder().build();
    }

    @Test
    void saveOrUpdate() {
        final var orderToSave = Order.builder().expert(expert)
                .customer(customer).subService(subService).id(2L).Price(100L).description("test1").duration(5).comment("subtest1").build();
        when(orderRepository.save(orderToSave)).thenReturn(orderToSave);
        final var response = orderService.saveOrUpdate(orderToSave);
        assertEquals(response,orderToSave);
        assertNotNull(response);
        verify(orderRepository,times(1)).save(any(Order.class));
        verifyNoMoreInteractions(orderRepository);

    }


    @Test
    void delete() {
        doNothing().when(orderRepository).delete(any());

        orderService.delete(new Order());
        verify(orderRepository, times(1)).delete(any());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void findById() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(orderToSave));
        final var findOrder = orderService.findById(anyLong());
        assertFalse(findOrder.isEmpty());
        assertEquals(findOrder.get(),orderToSave);
        verify(orderRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }
    


    @Test
    void findAll() {
        when(orderRepository.findAll()).thenReturn(List.of(new Order(), new Order()));
        assertEquals(orderService.findAll().size(),2);
        verify(orderRepository, times(1)).findAll();
        verifyNoMoreInteractions(orderRepository);
    }
}