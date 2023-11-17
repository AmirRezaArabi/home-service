package com.home.service.homeservice.domain;

import com.home.service.homeservice.domain.base.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String token;

    private LocalDate createdDate;

    @OneToOne
    private User user ;

    public ConfirmationToken(User user) {
        this.user=user;
        createdDate = LocalDate.now();
        token = UUID.randomUUID().toString();
    }



}