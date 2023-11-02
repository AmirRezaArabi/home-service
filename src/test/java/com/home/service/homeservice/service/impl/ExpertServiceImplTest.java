package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.repository.ExpertRepository;
import com.home.service.homeservice.repository.OrderRepository;
import com.home.service.homeservice.service.ExpertService;
import com.home.service.homeservice.service.OrderService;
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
class ExpertServiceImplTest {
    Expert expert;
    @MockBean
    ExpertRepository expertRepository ;

    @Autowired
    ExpertService expertService;

    @BeforeEach
    void setUp() {
         expert = Expert.builder().name("test").emailAddress("test@gmail.com").userName("test1234")
                .password("3211test").build();
    }


    @Test
    void saveOrUpdate() {
        final var ExpertToSave = Expert.builder().name("amir").emailAddress("fsfsf@gmail.com").userName("asdf1234")
                .password("3211asdf").build();
        when(expertRepository.save(ExpertToSave)).thenReturn(ExpertToSave);
        final var response = expertService.saveOrUpdate(ExpertToSave);
        //assertThat(response).usingRecursiveComparison().isEqualTo(ExpertToSave);
        assertEquals(response,ExpertToSave);
        assertNotNull(response);
        verify(expertRepository,times(1)).save(any(Expert.class));
        verifyNoMoreInteractions(expertRepository);

    }


    @Test
    void delete() {
        doNothing().when(expertRepository).delete(any());

        expertService.delete(new Expert());
        verify(expertRepository, times(1)).delete(any());
        verifyNoMoreInteractions(expertRepository);
    }

    @Test
    void findById() {
        when(expertRepository.findById(anyLong())).thenReturn(Optional.of(expert));
        final var findExpert = expertService.findById(anyLong());
        assertFalse(findExpert.isEmpty());
        assertEquals(findExpert.get(),expert);
        verify(expertRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(expertRepository);
    }

    @Test
    void findByName() {
        when(expertRepository.findByUserName(anyString())).thenReturn(Optional.of(expert));
        final var findExpert = expertService.findByUserName(anyString());
        assertFalse(findExpert.isEmpty());
        assertEquals(findExpert.get(),expert);
        verify(expertRepository, times(1)).findByUserName(anyString());
        verifyNoMoreInteractions(expertRepository);
    }


    @Test
    void findAll() {
        when(expertRepository.findAll()).thenReturn(List.of(new Expert(), new Expert()));
        assertEquals(expertService.findAll().size(),2);
        verify(expertRepository, times(1)).findAll();
        verifyNoMoreInteractions(expertRepository);
    }

    @Test
    void findServiceBuExpertName() {
        when(expertRepository.findByUserName(anyString())).thenReturn(Optional.of(expert));
        final var findService = expertService.findByUserName(anyString());
        assertFalse(findService.isEmpty());
        assertEquals(findService.get(),expert);
        verify(expertRepository, times(1)).findByUserName(anyString());
        verifyNoMoreInteractions(expertRepository);
    }
    @Test
    void changPassword() {
    }
    //todo : change password

}