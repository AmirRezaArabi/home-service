package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Order;
import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.domain.base.User;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.exception.TheAccountBalanceIsInsufficientException;
import com.home.service.homeservice.service.*;
import com.wf.captcha.SpecCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final ExpertService expertService;
    private final CustomerService customerService;
    private final WalletService walletService;
    private final OrderService orderService;


    @Override
    public Wallet depositWallet(Long price, Long walletId) {
        if (walletService.findById(walletId).isEmpty()) throw new IdIsNotExist("TheWalletIdIsNotExist");
        Wallet wallet = walletService.findById(walletId).get();
        wallet.setBalance(wallet.getBalance() + price);
        return walletService.saveOrUpdate(wallet) ;
    }

    @Override
    public Wallet withdrawWallet(Long price, Long walletId) {
        if (walletService.findById(walletId).isEmpty()) throw new IdIsNotExist("TheWalletIdIsNotExist");
        Wallet wallet = walletService.findById(walletId).get();
        if (wallet.getBalance() - price < 0)
            throw new TheAccountBalanceIsInsufficientException("The Account Balance Is Insufficient");
        wallet.setBalance(wallet.getBalance() - price);
        return walletService.saveOrUpdate(wallet);
    }


    @Override
    public void payWithWalletBalance(Long orderId) {
        Order order = orderService.findById(orderId).get();
        withdrawWallet(order.getPrice(),order.getCustomer().getWallet().getId());
        depositWallet(order.getPrice()*70/100,order.getExpert().getWallet().getId());
    }
//    public void payWithPaymentGateway(Long orderId){
//
//    }
@Override
public SpecCaptcha getCaptcha(int width, int height) {
    SpecCaptcha specCaptcha = new SpecCaptcha(width,height);
    return specCaptcha ;
}
}
