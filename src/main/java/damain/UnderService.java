package damain;

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
public class UnderService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @Column(unique = true)
    private String name ;
    @ManyToOne(cascade = CascadeType.ALL)
    private Service service;

    @ManyToMany(mappedBy = "underServices")
    private List<Expert> expert;

    private String describtion ;

    private Long baseprice ;
}
