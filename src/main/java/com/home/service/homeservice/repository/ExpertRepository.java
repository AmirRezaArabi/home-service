package com.home.service.homeservice.repository;


import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert,Long> {


    Optional<Expert> findByUsername(String username);

    List<Expert> findAll(@Nullable Specification<Expert> spec);
    //todo: save and update picture

    Optional<Expert> findByEmailAddress(String emailAddress);



}
