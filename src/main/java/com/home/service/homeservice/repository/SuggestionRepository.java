package com.home.service.homeservice.repository;


import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.Suggestion;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion,Long> {

    List<Suggestion> findAllByCustomerRequestOrderBySuggestionPriceAsc(CustomerRequest customerRequest) ;
}