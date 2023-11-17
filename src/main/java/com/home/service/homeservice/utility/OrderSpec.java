package com.home.service.homeservice.utility;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Order;
import com.home.service.homeservice.filter.ExpertFilter;
import com.home.service.homeservice.filter.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpec {

    public static Specification<Order> filterBy(OrderFilter orderFilter) {
        return Specification
                .where(hasCustomer(orderFilter.getCustomer()))
                .and(hasExpert(orderFilter.getExpert()))
                ;
    }

    private static Specification<Order> hasCustomer(Customer customer) {
        return ((root, query, cb) -> customer == null ? cb.conjunction() : cb.equal(root.get("customer"), customer));
    }

    private static Specification<Order> hasExpert(Expert expert) {
        return ((root, query, cb) -> expert == null ? cb.conjunction() : cb.equal(root.get("expert"), expert));
    }
}
