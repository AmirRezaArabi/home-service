package com.home.service.homeservice.repository;

import com.home.service.homeservice.domain.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,String> {

    Optional<ConfirmationToken> findByToken(String token);

    void deleteByToken(String token);
}
