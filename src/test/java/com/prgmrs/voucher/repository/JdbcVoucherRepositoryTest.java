package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.Wallet;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.vo.Amount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({JdbcVoucherRepository.class, JdbcWalletRepository.class, JdbcUserRepository.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:VoucherMgmtDDL-test.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:VoucherMgmtCleanup-test.sql")
})
class JdbcVoucherRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private JdbcUserRepository userRepository;

    @Test
    void save() {
        // Given
        UUID uuid = UUID.randomUUID();
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy discountStrategy = new FixedAmountDiscountStrategy(amount);
        Voucher voucher = new Voucher(uuid, discountStrategy);


        // When
        voucherRepository.save(voucher);

        // Then
        List<Voucher> retrievedVoucherList = voucherRepository.findAll();

        assertThat(retrievedVoucherList.size()).isEqualTo(1);
        assertThat(retrievedVoucherList.get(0).voucherId()).isEqualTo(voucher.voucherId());
        assertThat(((FixedAmountDiscountStrategy)retrievedVoucherList.get(0).discountStrategy()).amount().value()).isEqualTo(amount.value());
    }

    @Test
    void getAssignedVoucherListByUsername() {
        // Given
        UUID userUuid = UUID.randomUUID();
        String username = "tyler";
        User user = new User(userUuid, username);
        userRepository.save(user);

        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount1 = new Amount(500);
        FixedAmountDiscountStrategy discountStrategy1 = new FixedAmountDiscountStrategy(amount1);
        Voucher voucher1 = new Voucher(voucherUuid1, discountStrategy1);
        voucherRepository.save(voucher1);

        UUID voucherUuid2 = UUID.randomUUID();
        Amount amount2 = new Amount(400);
        FixedAmountDiscountStrategy discountStrategy2 = new FixedAmountDiscountStrategy(amount2);
        Voucher voucher2 = new Voucher(voucherUuid2, discountStrategy2);
        voucherRepository.save(voucher2);

        Wallet wallet1 = new Wallet(userUuid, voucherUuid1);
        walletRepository.save(wallet1);

        Wallet wallet2 = new Wallet(userUuid, voucherUuid2);
        walletRepository.save(wallet2);

        // When
        List<Voucher> assignedVoucherList = voucherRepository.getAssignedVoucherListByUsername(username);

        // Then
        assertThat(assignedVoucherList).isNotNull();
        assertThat(assignedVoucherList.size()).isEqualTo(2);
        assertThat(assignedVoucherList).containsExactlyInAnyOrder(voucher1, voucher2);

    }

    @Test
    void getAssignedVoucherList() {
        // Given
        UUID userUuid = UUID.randomUUID();
        String username = "tyler";
        User user = new User(userUuid, username);
        userRepository.save(user);

        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount1 = new Amount(500);
        FixedAmountDiscountStrategy discountStrategy1 = new FixedAmountDiscountStrategy(amount1);
        Voucher voucher1 = new Voucher(voucherUuid1, discountStrategy1);
        voucherRepository.save(voucher1);

        UUID voucherUuid2 = UUID.randomUUID();
        Amount amount2 = new Amount(400);
        FixedAmountDiscountStrategy discountStrategy2 = new FixedAmountDiscountStrategy(amount2);
        Voucher voucher2 = new Voucher(voucherUuid2, discountStrategy2);
        voucherRepository.save(voucher2);

        Wallet wallet1 = new Wallet(userUuid, voucherUuid1);
        walletRepository.save(wallet1);

        Wallet wallet2 = new Wallet(userUuid, voucherUuid2);
        walletRepository.save(wallet2);

        // When
        List<Voucher> assignedVoucherList = voucherRepository.getAssignedVoucherList();

        // Then
        assertThat(assignedVoucherList).isNotNull();
        assertThat(assignedVoucherList.size()).isEqualTo(2);
        assertThat(assignedVoucherList).containsExactlyInAnyOrder(voucher1, voucher2);
    }

    @Test
    void getNotAssignedVoucher() {
        // Given
        UUID userUuid = UUID.randomUUID();
        String username = "tyler";
        User user = new User(userUuid, username);
        userRepository.save(user);

        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount1 = new Amount(500);
        FixedAmountDiscountStrategy discountStrategy1 = new FixedAmountDiscountStrategy(amount1);
        Voucher voucher1 = new Voucher(voucherUuid1, discountStrategy1);
        voucherRepository.save(voucher1);

        UUID voucherUuid2 = UUID.randomUUID();
        Amount amount2 = new Amount(400);
        FixedAmountDiscountStrategy discountStrategy2 = new FixedAmountDiscountStrategy(amount2);
        Voucher voucher2 = new Voucher(voucherUuid2, discountStrategy2);
        voucherRepository.save(voucher2);

        // When
        List<Voucher> notAssignedVoucherList = voucherRepository.getNotAssignedVoucher();

        // Then
        assertThat(notAssignedVoucherList).isNotNull();
        assertThat(notAssignedVoucherList.size()).isEqualTo(2);
        assertThat(notAssignedVoucherList).containsExactlyInAnyOrder(voucher1, voucher2);
    }
}