package service.impl;

import domain.Suggestion;
import lombok.RequiredArgsConstructor;
import repository.SuggestionRepository;
import service.SuggestionService;

import java.util.List;
import java.util.Optional;

import static validation.EntityValidator.isValid;

@RequiredArgsConstructor
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
        return suggestion.getCustomerRequest().getCustomer().getUserName();
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
