package domain.base;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name ;
    @Column(unique = true)
    @NotNull(message = "userName can not be null")
    private String userName;
    @Size(min = 8,max = 8,message = "the password length should 8 char")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]*$")
    private String password;
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(unique = true)
    @NotNull
    private String emailAddress;
    private LocalDate registerDate;

}


