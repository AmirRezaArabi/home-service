package service;

import domain.Suggestion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface SuggestionService {
    Suggestion saveOrUpdate(Suggestion suggestion);

    String delete(Suggestion suggestion);

    Optional<Suggestion> findById(Long id);

    List<Suggestion> findAll();
}
