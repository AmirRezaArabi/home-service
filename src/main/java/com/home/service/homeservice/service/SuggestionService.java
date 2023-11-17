package com.home.service.homeservice.service;

import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.Suggestion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface SuggestionService {
    Suggestion saveOrUpdate(Suggestion suggestion);

    String delete(Suggestion suggestion);

    Suggestion findById(Long id);

    List<Suggestion> findAll();

    List<Suggestion> findAllByCustomerRequestOrderBySuggestionPriceAsc(CustomerRequest customerRequest) ;

}
