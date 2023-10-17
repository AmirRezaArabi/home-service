package com.home.service.homeservice.domain;

import com.home.service.homeservice.domain.base.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Customer extends User {

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerRequest> customerRequests = new ArrayList<>();
    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        return "Customer{" +
                "wallet=" + wallet +
                ", customerRequests=" + customerRequests +
                ", orders=" + orders +
                "} " + super.toString();
    }

    @Builder

    public Customer(Long id, String name, @NotNull(message = "userName can not be null")
    String userName, @Size(min = 8, max = 8, message = "the password length should 8 char")
                    @Pattern(regexp = "^(?=.+\\d)(?=.+[a-zA-Z]).{8}$") String password,
                    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
                    @NotNull String emailAddress, LocalDate registerDate, Wallet wallet
            , Wallet wallet1, List<CustomerRequest> customerRequests, List<Order> orders) {
        super(id, name, userName, password, emailAddress, registerDate, wallet);
        this.wallet = wallet1;
        this.customerRequests = customerRequests;
        this.orders = orders;
    }
}
