package damain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_tb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @NotNull
    @OneToOne
    private Service service;
    @NotNull
    @OneToOne
    private UnderService underService ;
    @NotNull
    @ManyToOne
    private Customer customer ;
    @NotNull
    @ManyToOne
    private Expert expert;
    @NotNull
    private Long Price ;

    private String description ;

    private LocalDate startDay;

    private String comment;




}
