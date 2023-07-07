package kr.co.programmers.springbootbasic.wallet.repository.impl;

import kr.co.programmers.springbootbasic.customer.repository.CustomerRepository;
import kr.co.programmers.springbootbasic.voucher.repository.VoucherRepository;
import kr.co.programmers.springbootbasic.wallet.repository.WalletRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JdbcWalletRepositoryTest {

    @Autowired
    WalletRepository walletRepository;
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void breakDown() {

    }

    @Test
    @DisplayName("테스트테스트")
    void test() {

    }
}
