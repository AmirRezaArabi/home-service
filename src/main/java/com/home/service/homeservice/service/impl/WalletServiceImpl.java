package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import com.home.service.homeservice.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet saveOrUpdate(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public String delete(Wallet wallet) {
         walletRepository.delete(wallet);
        return wallet.getUser().getUsername();
    }

    @Override
    public Wallet findById(Long id) {
        return walletRepository.findById(id).orElseThrow(()->new IdIsNotExist("the id not fount"));
    }

    @Override
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }



}
