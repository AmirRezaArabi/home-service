package com.home.service.homeservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @NotNull
    @Column(unique = true)
    private String name ;
    @OneToMany(mappedBy = "service",cascade = CascadeType.ALL)
    private List<SubService> subServices = new ArrayList<>() ;
}

