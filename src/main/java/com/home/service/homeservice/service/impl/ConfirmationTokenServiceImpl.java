package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.ConfirmationToken;
import com.home.service.homeservice.exception.TheTokenNotFoundException;
import com.home.service.homeservice.repository.ConfirmationTokenRepository;
import com.home.service.homeservice.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ConfirmationToken findByToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(()->new TheTokenNotFoundException("token not found"));
    }

    @Override
    public ConfirmationToken saveOrUpdate(ConfirmationToken confirmationToken) {
        return confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void deleteByToken(String token) {
        confirmationTokenRepository.deleteByToken(token);
    }
}
