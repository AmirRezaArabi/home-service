package domain;

import domain.base.User;
import domain.enums.EXPERT_STATUS;
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
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Expert extends User {

    @Enumerated(EnumType.STRING)
    private EXPERT_STATUS expert_status;
    private int Score;
    @OneToOne
    private Service service;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<SubService> subServices = new ArrayList<>();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] profilePicture;

    @Override
    public String toString() {
        return "Expert{" +
                "expert_status=" + expert_status +
                ", Score=" + Score +
                ", service=" + service +
                ", subServices=" + subServices +
                "} " + super.toString();
    }

    @Builder
    public Expert(Long id, String name, @NotNull(message = "userName can not be null") String userName, @Size(min = 8, max = 8, message = "the password length should 8 char") @Pattern(regexp = "^[a-zA-Z0-9_.-]*$") String password, @Pattern(regexp = "^(?=.{1,64}@)[\\\\p{L}0-9_-]+(\\\\.[\\\\p{L}0-9_-]+)*@[^-][\\\\p{L}0-9-]" +
            "+(\\\\.[\\\\p{L}0-9-]+)*(\\\\.[\\\\p{L}]{2,})$") @NotNull String emailAddress, LocalDate registerDate, EXPERT_STATUS expert_status, int score, Service service, List<SubService> subServices, byte[] profilePicture) {
        super(id, name, userName, password, emailAddress, registerDate);
        this.expert_status = expert_status;
        Score = score;
        this.service = service;
        this.subServices = subServices;
        this.profilePicture = profilePicture;
    }
}

