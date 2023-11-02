package com.home.service.homeservice.utility;

import com.home.service.homeservice.domain.Customer;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor
public class CustomerSpec {

    public static Specification<Customer> filterBy(ExpertFilter expertFilter) {
        return Specification
                .where(hasName(expertFilter.getName()))
                .and(hasEmail(expertFilter.getEmailAddress()));
    }

    private static Specification<Customer> hasName(String name) {
        return ((root, query, cb) -> name == null || name.isEmpty() ? cb.conjunction() : cb.equal(root.get("name"), name));
    }

    private static Specification<Customer> hasEmail(String emailAddress) {
        return (root, query, cb) -> emailAddress == null ? cb.conjunction() : cb.equal(root.get("emailAddress"), emailAddress);
    }
}
