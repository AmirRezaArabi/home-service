package com.home.service.homeservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @OneToOne(cascade = CascadeType.MERGE)
    private CustomerRequest customerRequest ;

    @OneToOne
    private Expert expert;

    private Long suggestionPrice ;

    private LocalDate suggestionDate;

    private LocalDate startWorkDay ;

    private int duration ;
}
