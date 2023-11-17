package com.home.service.homeservice.domain;

import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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


@Table(name = "order_tb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @NotNull
    @ManyToOne
    private SubService subService ;
    @NotNull
    @ManyToOne
    private Customer customer ;
    @NotNull
    @ManyToOne
    private Expert expert;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private REQUEST_STATUS status;
    @NotNull
    private Long Price ;

    private int duration;

    private String description ;

    private LocalDate startDay;

    private String comment;

    private int score;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", subService=" + subService.getName() +
                ", request_status=" + status +
                ", Price=" + Price +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                ", startDay=" + startDay +
                ", comment='" + comment + '\'' +
                ", score=" + score +
                '}';
    }
}
