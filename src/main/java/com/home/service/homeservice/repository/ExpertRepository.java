package com.home.service.homeservice.repository;


import com.home.service.homeservice.domain.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert,Long> {


    Optional<Expert> findByUserName(String userName);

    //todo: save and update picture



}
