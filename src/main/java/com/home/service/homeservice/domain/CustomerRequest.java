package com.home.service.homeservice.domain;

import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class CustomerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @ManyToOne
    private Customer customer;
    @NotNull
    @OneToOne
    private SubService subService;

    private Long suggestionPrice ;

    private String description;

    private LocalDate startDay;

    private String address;
    @Enumerated(EnumType.STRING)
    private REQUEST_STATUS request_status;


}
