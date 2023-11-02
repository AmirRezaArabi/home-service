package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Service;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ServiceServiceImplTest {

    @MockBean
    com.home.service.homeservice.repository.ServiceRepository serviceRepository;

    @Autowired
    com.home.service.homeservice.service.ServiceService serviceService;

    Service service;
    @BeforeEach
    void setUp() {
        service = Service.builder().name("test").id(1L).build();

    }

    @Test
    void saveOrUpdate() {
        when(serviceRepository.save(service)).thenReturn(service);
        final var response = serviceService.saveOrUpdate(service);
        assertEquals(response,service);
        assertNotNull(response);
        verify(serviceRepository,times(1)).save(any(Service.class));
        verifyNoMoreInteractions(serviceRepository);

    }

    @Test
    void delete() {
        doNothing().when(serviceRepository).delete(any());

        serviceService.delete(new Service());
        verify(serviceRepository, times(1)).delete(any());
        verifyNoMoreInteractions(serviceRepository);
    }

    @Test
    void findById() {
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.of(service));
        final var findservice = serviceService.findById(anyLong());
        assertFalse(findservice.isEmpty());
        assertEquals(findservice.get(),service);
        verify(serviceRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(serviceRepository);
    }

    @Test
    void findByName() {
        when(serviceRepository.findByName(anyString())).thenReturn(Optional.of(service));
        final var findService = serviceService.findByName(anyString());
        assertFalse(findService.isEmpty());
        assertEquals(findService.get(),service);
        verify(serviceRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(serviceRepository);
    }

    @Test
    void findAll() {
        when(serviceRepository.findAll()).thenReturn(List.of(new Service(), new Service()));
        assertEquals(serviceService.findAll().size(),2);
        verify(serviceRepository, times(1)).findAll();
        verifyNoMoreInteractions(serviceRepository);
    }
}