package com.home.service.homeservice.repository;

import com.home.service.homeservice.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByUsername(String username);
}
