package com.home.service.homeservice.domain;

import com.home.service.homeservice.domain.base.User;
import com.home.service.homeservice.domain.enums.EXPERT_STATUS;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public String toString() {
        return "Expert{" +
                "expert_status=" + expert_status +
                ", Score=" + score +
                ", subServices=" + subServices +
                ", profilePicture=" + Arrays.toString(profilePicture) +
                "} " + super.toString();
    }

    @Builder
    public Expert(Long id, String name, @NotNull(message = "userName can not be null") String userName,
                  @Size(min = 8, max = 8, message = "the password length should 8 char")
                  @Pattern(regexp = "^(?=.+\\d)(?=.+[a-zA-Z]).{8}$") String password,
                  @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
                  @NotNull String emailAddress, LocalDate registerDate, Wallet wallet,
                  EXPERT_STATUS expert_status, int score, List<SubService> subServices, byte[] profilePicture,
                  List<String> comments) {
        super(id, name, userName, password, emailAddress, registerDate, wallet);
        this.expert_status = expert_status;
        this.score = score;
        this.subServices = subServices;
        this.profilePicture = profilePicture;
        this.comments = comments;
    }
}


