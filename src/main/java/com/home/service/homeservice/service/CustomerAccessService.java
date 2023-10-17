package com.home.service.homeservice.service;

import com.home.service.homeservice.domain.Suggestion;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CustomerAccessService {

    boolean placeAnRequest(Long subServiceId, String customerUserName, LocalDate time, Long suggestionPrice, String description, String address);

    boolean setScoreByCustomer(Long orderId, int score);

    boolean setCommentByCustomer(Long orderId, String comment);

    List<Suggestion> showSuggestion(Long customerRequestId);


    boolean chooseSuggestion(Long suggestionId);

    boolean changeRequestStatusToStarted(Long orderId);


    boolean changeRequestStatusToDone(Long orderId);
}
