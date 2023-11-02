package com.home.service.homeservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @Column(unique = true)
    private String name ;
    @ManyToOne
    private Service service;

    @ManyToMany(mappedBy = "subServices")
    private List<Expert> expert;

    private String description;

    private Long basePrice;
}
