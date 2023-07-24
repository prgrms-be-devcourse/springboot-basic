package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.Wallet;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.wrapper.Amount;
import com.prgmrs.voucher.model.wrapper.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("JDBC 지갑 레포지토리 레이어를 테스트한다.")

@JdbcTest
@Import({JdbcWalletRepository.class, JdbcUserRepository.class, JdbcVoucherRepository.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:VoucherMgmtDDL-test.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:VoucherMgmtCleanup-test.sql")
})
class JdbcWalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    private User userTyler;
    private Voucher voucherWithFixedAmountOf500;

    @BeforeEach
    void setUp() {
        UUID uuidTyler = UUID.randomUUID();
        userTyler = new User(uuidTyler, new Username("tyler"));

        UUID voucherUuid = UUID.randomUUID();
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy discountStrategy = new FixedAmountDiscountStrategy(amount);
        voucherWithFixedAmountOf500 = new Voucher(voucherUuid, discountStrategy);
    }

    @Test
    @DisplayName("지갑을 저장한다.")
    void Save_Wallet_NotThrowingException() {
        // Given
        userRepository.save(userTyler);
        voucherRepository.save(voucherWithFixedAmountOf500);

        Wallet wallet = new Wallet(userTyler.userId(), voucherWithFixedAmountOf500.voucherId());

        // When
        Executable executable = () -> walletRepository.save(wallet);

        // Then
        assertDoesNotThrow(executable);
    }

    @Test
    @DisplayName("지갑 저장을 해지한다.")
    void Free_Wallet_NotThrowingException() {
        // Given
        userRepository.save(userTyler);
        voucherRepository.save(voucherWithFixedAmountOf500);

        Wallet wallet = new Wallet(userTyler.userId(), voucherWithFixedAmountOf500.voucherId());
        walletRepository.save(wallet);

        // When
        Executable executable = () -> walletRepository.remove(wallet);

        // Then
        assertDoesNotThrow(executable);
    }
}