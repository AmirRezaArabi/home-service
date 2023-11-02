package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.domain.base.User;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.exception.TheInputInformationIsNotValidException;
import com.home.service.homeservice.repository.WalletRepository;
import com.home.service.homeservice.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WalletServiceImplTest {
    //todo: hanooz kar dare

    List<Wallet> walletList;
    Wallet wallet;
    Wallet wallet1;
    Wallet walletForValidation;
    @BeforeEach
    void setUp() {
       walletList = new ArrayList<>(
                List.of(
                        Wallet.builder().Balance(100L).build(),
                        Wallet.builder().Balance(200L).build(),
                        Wallet.builder().Balance(300L).build(),
                        Wallet.builder().Balance(400L).build()
                ));
       Customer customer = Customer.builder().name("amir").emailAddress("fsfsf@gmail.com").userName("asdf1234")
               .password("3211asdf").build();
         wallet = Wallet.builder().id(1L).user(customer).Balance(100L).build();
         walletForValidation=Wallet.builder().build();
    }

    @MockBean
    private WalletRepository walletRepository;

    @Autowired
    private WalletService walletService ;



    @Test
    void saveOrUpdate() {
       when(walletRepository.findById(wallet.getId()))
               .thenReturn(Optional.empty());
        when(walletRepository.save(wallet)).thenReturn(wallet);
        Wallet savedWallet = walletService.saveOrUpdate(wallet);
        System.out.println(savedWallet);
        assertNotNull(savedWallet);
    }


    @Test
    void delete() {
   assertEquals (walletService.delete(wallet),wallet.getUser().getUserName());

    }

    @Test
    void findById() {

        when(walletRepository.findById(1L)).thenReturn(Optional.ofNullable(wallet));
        assertEquals(walletService.findById(1L),Optional.ofNullable(wallet));
    }

//    @Test
//    public void throwExceptionWhenQuarterIdNotFound() {
//        Long id = anyLong();
//        when(walletRepository.findById(id))
//                .thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> walletRepository.findById(id))
//                .isInstanceOf(IdIsNotExist.class)
//                .hasMessageContaining("PUT_YOUR_EXCEPTION_MESSAGE_HERE");
//        }



    @Test
    void findAll() {

        when(walletRepository.findAll()).thenReturn(walletList);
        assertEquals(4,walletService.findAll().size());
        assertEquals(walletList,walletService.findAll());
    }
}