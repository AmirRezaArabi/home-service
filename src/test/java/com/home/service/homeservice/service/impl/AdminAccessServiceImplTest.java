package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.service.AdminAccessService;
import com.home.service.homeservice.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AdminAccessServiceImplTest {

    @MockBean
    ServiceService serviceService;

    @Autowired
    AdminAccessService adminAccessService;

    @Test
    void assService()
    {
        Service service = Service.builder().name("test").build();
        when(serviceService.saveOrUpdate(any())).thenReturn(service);
        boolean test = adminAccessService.addService("test");
        assertTrue(test);
        verify(serviceService,times(1)).saveOrUpdate(any());
        verifyNoMoreInteractions(serviceService);
    }

}