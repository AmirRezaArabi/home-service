package com.home.service.homeservice.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpertFilter {
    private  String name ;
    private  String emailAddress;
    private int score;
    private int maxScore;
    private int minScore;
}
