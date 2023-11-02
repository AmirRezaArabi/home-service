package com.home.service.homeservice.utility;

import com.home.service.homeservice.domain.Expert;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ExpertSpec {

    private static int score;

    public static Specification<Expert> filterBy(ExpertFilter expertFilter) {
        return Specification
                .where(hasName(expertFilter.getName()))
                .and(hasEmail(expertFilter.getEmailAddress()))
                .and(hasScore(expertFilter.getScore()))
                ;
    }

    private static Specification<Expert> hasName(String name) {
        return ((root, query, cb) -> name == null || name.isEmpty() ? cb.conjunction() : cb.equal(root.get("name"), name));
    }

    private static Specification<Expert> hasEmail(String emailAddress) {
        return (root, query, cb) -> emailAddress == null || emailAddress.isEmpty() ? cb.conjunction() : cb.equal(root.get("emailAddress"), emailAddress);
    }
    private static Specification<Expert> hasScore(int score) {
        return (root, query, cb) -> cb.equal(root.get("score"), score);
    }
}

