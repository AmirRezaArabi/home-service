package com.home.service.homeservice.service;

import com.home.service.homeservice.domain.Wallet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface WalletService {
    Wallet saveOrUpdate(Wallet wallet);
    String delete(Wallet wallet);
    Optional<Wallet> findById(Long id);
    List<Wallet> findAll();


}