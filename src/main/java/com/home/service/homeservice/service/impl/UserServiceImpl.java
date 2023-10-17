package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.exception.TheAccountBalanceIsInsufficientException;
import com.home.service.homeservice.service.UserService;
import com.home.service.homeservice.service.WalletService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final WalletService walletService;

    @Override
    public boolean depositWallet(Long price, Long walletId) {
        if (walletService.findById(walletId).isEmpty())
            throw new IdIsNotExist("TheWalletIdIsNotExist");
        Wallet wallet = walletService.findById(walletId).get();
        wallet.setBalance(wallet.getBalance() + price);
        return walletService.saveOrUpdate(wallet) != null;
    }

    @Override
    public boolean withdrawWallet(Long price, Long walletId) {
        if (walletService.findById(walletId).isEmpty())
            throw new IdIsNotExist("TheWalletIdIsNotExist");
        Wallet wallet = walletService.findById(walletId).get();
        if (wallet.getBalance() - price < 0)
            throw new TheAccountBalanceIsInsufficientException("The Account Balance Is Insufficient");
        wallet.setBalance(wallet.getBalance() - price);
        return walletService.saveOrUpdate(wallet) != null;
    }
}
