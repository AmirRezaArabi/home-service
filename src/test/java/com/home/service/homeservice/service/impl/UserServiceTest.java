package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.service.UserService;
import com.home.service.homeservice.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @MockBean
    WalletService walletService;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void depositWallet() {
        Wallet wallet = Wallet.builder().id(1L).Balance(123L).build();
        when(walletService.findById(anyLong())).thenReturn(Optional.of(wallet));
        when(walletService.saveOrUpdate(any())).thenReturn(wallet);
        userService.depositWallet(100L,1L);

        assertEquals(wallet.getBalance(),223L);


    }

    @Test
    void withdrawWallet() {
        Wallet wallet = Wallet.builder().id(1L).Balance(123L).build();
        when(walletService.findById(anyLong())).thenReturn(Optional.of(wallet));
        when(walletService.saveOrUpdate(any())).thenReturn(wallet);
        userService.withdrawWallet(100L,1L);

        assertEquals(wallet.getBalance(),23L);
    }
}