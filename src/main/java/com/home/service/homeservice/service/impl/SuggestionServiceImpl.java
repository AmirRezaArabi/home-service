package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Suggestion;
import lombok.RequiredArgsConstructor;
import com.home.service.homeservice.repository.SuggestionRepository;
import com.home.service.homeservice.service.SuggestionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.home.service.homeservice.validation.EntityValidator.isValid;

@RequiredArgsConstructor
@Service

public class SuggestionServiceImpl implements SuggestionService {
    private final SuggestionRepository suggestionRepository;


    @Override
    public Suggestion saveOrUpdate(Suggestion suggestion) {
        if (!isValid(suggestion))
            return null;
        return suggestionRepository.save(suggestion);
    }


    @Override
    public String delete(Suggestion suggestion) {
        suggestionRepository.delete(suggestion);
        return suggestion.getExpert().getUserName();
    }

    @Override
    public Optional<Suggestion> findById(Long id) {
        return suggestionRepository.findById(id);
    }

    @Override
    public List<Suggestion> findAll() {
        return suggestionRepository.findAll();
    }
}
