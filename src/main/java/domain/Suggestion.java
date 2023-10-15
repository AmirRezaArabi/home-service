package domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @OneToOne
    private CustomerRequest customerRequest ;

    private Long suggestionPrice ;

    private LocalDate SuggestionDate;

    private LocalDate startWorkDay ;

    private int duration ;
}
