package com.home.service.homeservice.domain;

import com.home.service.homeservice.domain.base.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Customer extends User {
    @OneToMany(mappedBy = "customer", cascade =  {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.REMOVE})
    private List<CustomerRequest> customerRequests = new ArrayList<>();
    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();



}

