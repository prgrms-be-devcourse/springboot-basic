package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.repository.wallet.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class WalletServiceTest {

    @MockBean
    private WalletRepository walletRepository;

    @Autowired
    private WalletService walletService;

    @Test
    @DisplayName("특정 고객에게 바우처를 할당할 수 있다.")
    void createWallet() {

    }

    @Test
    @DisplayName("고객의 바우처 목록을 조회할 수 있다.")
    void getCustomerWallets() {
    }

    @Test
    @DisplayName("바우처의 고객 목록을 조회할 수 있다.")
    void getVoucherWallets() {
    }

    @Test
    @DisplayName("고객의 바우처를 삭제할 수 있다.")
    void deleteWallet() {
    }
}
