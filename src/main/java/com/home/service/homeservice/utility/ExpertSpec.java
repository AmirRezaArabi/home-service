package com.home.service.homeservice.utility;

import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.filter.ExpertFilter;
import org.springframework.data.jpa.domain.Specification;

public class ExpertSpec {


    public static Specification<Expert> filterBy(ExpertFilter expertFilter) {
        return Specification
                .where(hasName(expertFilter.getName()))
                .and(hasEmail(expertFilter.getEmailAddress()))
                .and(betweenScores(expertFilter.getMinScore(),expertFilter.getMaxScore()))
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

    private static Specification<Expert> betweenScores(int minScore, int maxScore)
    {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("score"),minScore,maxScore));
    }




}

