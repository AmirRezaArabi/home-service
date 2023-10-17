package com.home.service.homeservice.repository;

import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.domain.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService,Long> {

    Optional<SubService> findSubServiceByName(String name);

    @Query("select Service from SubService s where s.name=:name")
    Optional<Service> findServiceBuSubServiceName(@Param("name") String name);

}