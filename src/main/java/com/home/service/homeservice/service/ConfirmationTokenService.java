package com.home.service.homeservice.service;

import com.home.service.homeservice.domain.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {

    ConfirmationToken findByToken(String token);
    ConfirmationToken saveOrUpdate(ConfirmationToken confirmationToken);

    void deleteByToken(String token);

}
