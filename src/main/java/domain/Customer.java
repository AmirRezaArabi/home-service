package domain;

import domain.base.User;
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

    @OneToMany(mappedBy = "customer")
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
    public Customer(Long id, String name, @NotNull(message = "userName can not be null") String userName, @Size(min = 8, max = 8, message = "the password length should 8 char") @Pattern(regexp = "^[a-zA-Z0-9_.-]*$") String password, @Pattern(regexp = "^(?=.{1,64}@)[\\\\p{L}0-9_-]+(\\\\.[\\\\p{L}0-9_-]+)*@[^-][\\\\p{L}0-9-]" +
            "+(\\\\.[\\\\p{L}0-9-]+)*(\\\\.[\\\\p{L}]{2,})$") @NotNull String emailAddress, LocalDate registerDate, Wallet wallet, List<CustomerRequest> customerRequests, List<Order> orders) {
        super(id, name, userName, password, emailAddress, registerDate);
        this.wallet = wallet;
        this.customerRequests = customerRequests;
        this.orders = orders;
    }
}
