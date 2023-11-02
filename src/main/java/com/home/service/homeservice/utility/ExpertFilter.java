package com.home.service.homeservice.utility;

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
    private Boolean maxScore;
    private Boolean minScore;
}
