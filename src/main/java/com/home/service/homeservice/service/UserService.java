package com.home.service.homeservice.service;

import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.domain.base.User;
import com.wf.captcha.SpecCaptcha;

public interface UserService {


    Wallet depositWallet (Long price , Long walletId);
    Wallet withdrawWallet (Long price , Long walletId);

    void payWithWalletBalance(Long orderId);

    SpecCaptcha getCaptcha(int witch , int height);
}
