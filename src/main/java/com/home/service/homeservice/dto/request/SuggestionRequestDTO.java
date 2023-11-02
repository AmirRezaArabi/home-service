package com.home.service.homeservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionRequestDTO {

    private Long customerRequestId;
    private Long expertId;
    private Long price;
    private Timestamp startWorkDay;
    private int duration;

}
