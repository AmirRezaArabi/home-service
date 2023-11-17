package com.home.service.homeservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResForScoresDTO {
    private int score;
    private String customerUsername;
    private String expertUsername;
}
