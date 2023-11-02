package com.home.service.homeservice.dto.response;

import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.Expert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SuggestionResponseDTO {

    private Long id ;
    private Long customerRequestId;
    private Long expertId;
    private Long suggestionPrice;
    private LocalDate suggestionDate;
    private LocalDate startWorkDay;
    private int duration;

}
