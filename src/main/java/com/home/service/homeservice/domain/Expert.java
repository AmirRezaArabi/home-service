package com.home.service.homeservice.domain;

import com.home.service.homeservice.domain.base.User;
import com.home.service.homeservice.domain.enums.EXPERT_STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Expert extends User {

    @Enumerated(EnumType.STRING)
    private EXPERT_STATUS expert_status;

    private int score;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<SubService> subServices = new ArrayList<>();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] profilePicture;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "comments", joinColumns = @JoinColumn(name = "id"))
    private List<String> comments = new ArrayList<>();



}


