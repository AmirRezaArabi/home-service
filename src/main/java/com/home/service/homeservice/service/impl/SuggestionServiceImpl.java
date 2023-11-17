package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.Suggestion;
import com.home.service.homeservice.exception.IdIsNotExist;
import lombok.RequiredArgsConstructor;
import com.home.service.homeservice.repository.SuggestionRepository;
import com.home.service.homeservice.service.SuggestionService;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service

public class SuggestionServiceImpl implements SuggestionService {
    private final SuggestionRepository suggestionRepository;


    @Override
    public Suggestion saveOrUpdate(Suggestion suggestion) {
        return suggestionRepository.save(suggestion);
    }


    @Override
    public String delete(Suggestion suggestion) {
        suggestionRepository.delete(suggestion);
        return suggestion.getExpert().getUsername();
    }

    @Override
    public Suggestion findById(Long id) {
        return suggestionRepository.findById(id).orElseThrow(()->new IdIsNotExist("the id is not found"));
    }

    @Override
    public List<Suggestion> findAll() {
        return suggestionRepository.findAll();
    }

    @Override
    public List<Suggestion> findAllByCustomerRequestOrderBySuggestionPriceAsc(CustomerRequest customerRequest) {
        return suggestionRepository.findAllByCustomerRequestOrderBySuggestionPriceAsc(customerRequest);
    }
}
