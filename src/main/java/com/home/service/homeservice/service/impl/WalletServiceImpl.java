package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import com.home.service.homeservice.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.home.service.homeservice.validation.EntityValidator.isValid;
@RequiredArgsConstructor
@Service

public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet saveOrUpdate(Wallet wallet) {
        if (!isValid(wallet))
            return null;
        return walletRepository.save(wallet);
    }

    @Override
    public String delete(Wallet wallet) {
         walletRepository.delete(wallet);
        return wallet.getUser().getUserName();
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return walletRepository.findById(id);
    }

    @Override
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }



}
