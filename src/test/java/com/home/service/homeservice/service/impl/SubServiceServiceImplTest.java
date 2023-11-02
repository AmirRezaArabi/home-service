package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.domain.SubService;
import com.home.service.homeservice.repository.SubServiceRepository;
import com.home.service.homeservice.service.SubServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SubServiceServiceImplTest {
    Service service;
    SubService subService;

    @MockBean
    SubServiceRepository subServiceRepository;

    @Autowired
    SubServiceService subServiceService;

    @BeforeEach
    void setUp() {
        service = Service.builder().name("test").id(1L).build();
        subService = SubService.builder().service(service).description("testtest").basePrice(1000L).name("subtest").build();

    }
    //todo: invalid test

    @Test
    void saveOrUpdate() {
        final var subServiceToSave = SubService.builder().service(service).description("test1").basePrice(2000L).name("subtest1").build();
        when(subServiceRepository.save(subServiceToSave)).thenReturn(subServiceToSave);
        final var response = subServiceService.saveOrUpdate(subServiceToSave);
        //assertThat(response).usingRecursiveComparison().isEqualTo(subServiceToSave);
        assertEquals(response,subServiceToSave);
        assertNotNull(response);
        verify(subServiceRepository,times(1)).save(any(SubService.class));
        verifyNoMoreInteractions(subServiceRepository);

    }


    @Test
    void delete() {
        doNothing().when(subServiceRepository).delete(any());

        subServiceService.delete(new SubService());
        verify(subServiceRepository, times(1)).delete(any());
        verifyNoMoreInteractions(subServiceRepository);
    }

    @Test
    void findById() {
        when(subServiceRepository.findById(anyLong())).thenReturn(Optional.of(subService));
        final var findSubService = subServiceService.findById(anyLong());
        assertFalse(findSubService.isEmpty());
        assertEquals(findSubService.get(),subService);
        verify(subServiceRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(subServiceRepository);
    }

    @Test
    void findByName() {
        when(subServiceRepository.findSubServiceByName(anyString())).thenReturn(Optional.of(subService));
        final var findSubService = subServiceService.findByName(anyString());
        assertFalse(findSubService.isEmpty());
        assertEquals(findSubService.get(),subService);
        verify(subServiceRepository, times(1)).findSubServiceByName(anyString());
        verifyNoMoreInteractions(subServiceRepository);
    }


    @Test
    void findAll() {
        when(subServiceRepository.findAll()).thenReturn(List.of(new SubService(), new SubService()));
        assertEquals(subServiceService.findAll().size(),2);
        verify(subServiceRepository, times(1)).findAll();
        verifyNoMoreInteractions(subServiceRepository);
    }

    @Test
    void findServiceBuSubServiceName() {
        when(subServiceRepository.findServiceBuSubServiceName(anyString())).thenReturn(Optional.of(service));
        final var findService = subServiceService.findServiceBuSubServiceName(anyString());
        assertFalse(findService.isEmpty());
        assertEquals(findService.get(),service);
        verify(subServiceRepository, times(1)).findServiceBuSubServiceName(anyString());
        verifyNoMoreInteractions(subServiceRepository);
    }
}