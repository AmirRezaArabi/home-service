package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Suggestion;
import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.repository.SuggestionRepository;
import com.home.service.homeservice.service.SuggestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SuggestionServiceImplTest {
    //todo: hanooz kar dare
    Suggestion suggestion ;
    Suggestion suggestion1;
    Expert expert;
    List<Suggestion> suggestions;

    @MockBean
    private SuggestionRepository suggestionRepository;

    @Autowired
    private SuggestionService suggestionService;


    @BeforeEach
    void setUp() {
        expert = Expert.builder().name("amir").emailAddress("fsfsf@gmail.com").userName("asdf1234")
                .password("3211asdf").build();

        suggestion = Suggestion.builder().suggestionDate(LocalDate.now()).suggestionPrice(100L).duration(3)
                .startWorkDay(LocalDate.of(2024,2,2)).id(1L).expert(expert).build();
        suggestions = new ArrayList<Suggestion>(List.of(
                Suggestion.builder().suggestionDate(LocalDate.now()).suggestionPrice(100L).duration(3).startWorkDay(LocalDate.of(2024,2,2)).id(1L).build(),
                Suggestion.builder().suggestionDate(LocalDate.now()).suggestionPrice(120L).duration(4).startWorkDay(LocalDate.of(2025,2,2)).id(2L).build(),
                Suggestion.builder().suggestionDate(LocalDate.now()).suggestionPrice(130L).duration(5).startWorkDay(LocalDate.of(2026,2,2)).id(3L).build(),
                Suggestion.builder().suggestionDate(LocalDate.now()).suggestionPrice(140L).duration(6).startWorkDay(LocalDate.of(2027,2,2)).id(4L).build()

        ));

    }

    @Test
    void saveOrUpdate() {
        when(suggestionRepository.findById(suggestion.getId()))
                .thenReturn(Optional.empty());
        when(suggestionRepository.save(suggestion)).thenReturn(suggestion);
        Suggestion savedSuggestion = suggestionService.saveOrUpdate(suggestion);
        System.out.println(savedSuggestion);
        assertNotNull(savedSuggestion);
    }


    @Test
    void delete() {
        doNothing().when(suggestionRepository).delete(any());
        assertEquals (suggestionService.delete(suggestion),suggestion.getExpert().getUserName());
        verify(suggestionRepository, times(1)).delete(suggestion);
        verifyNoMoreInteractions(suggestionRepository);
    }

    @Test
    void findById() {
        when(suggestionRepository.findById(1L)).thenReturn(Optional.ofNullable(suggestion));
        assertEquals(suggestionService.findById(1L),Optional.ofNullable(suggestion));
    }

    @Test
    void findAll() {
        when(suggestionRepository.findAll()).thenReturn(suggestions);
        assertEquals(4,suggestionService.findAll().size());
        assertEquals(suggestions,suggestionService.findAll());
    }
}