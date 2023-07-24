package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.Wallet;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.wrapper.Amount;
import com.prgmrs.voucher.model.wrapper.Percent;
import com.prgmrs.voucher.model.wrapper.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
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
@DisplayName("JDBC 바우처 레포지토리 레이어를 테스트한다.")
class JdbcVoucherRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private JdbcUserRepository userRepository;

    private UUID uuidTyler;
    private Username usernameTyler;
    private User userTyler;
    private Voucher voucherFixedAmountOf500;
    private Voucher voucherPercentDiscountOf30;
    private UUID voucherUuid1;
    private UUID voucherUuid2;
    private FixedAmountDiscountStrategy fixedAmountDiscountStrategy;
    private PercentDiscountStrategy percentDiscountStrategy;

    @BeforeEach
    void setUp() {
        voucherUuid1 = UUID.randomUUID();
        Amount amount = new Amount(500);
        fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);

        voucherUuid2 = UUID.randomUUID();
        Percent percent = new Percent(30);
        percentDiscountStrategy = new PercentDiscountStrategy(percent);

        uuidTyler = UUID.randomUUID();
        usernameTyler = new Username("tyler");
        userTyler = new User(uuidTyler, usernameTyler);
        userRepository.save(userTyler);
    }

    @Test
    @DisplayName("바우처를 저장한다.")
    void Save_NoParam_SameVoucher() {
        // Given
        voucherFixedAmountOf500 = new Voucher(voucherUuid1, fixedAmountDiscountStrategy);
        voucherPercentDiscountOf30 = new Voucher(voucherUuid2, percentDiscountStrategy);

        // When
        voucherRepository.save(voucherFixedAmountOf500);
        voucherRepository.save(voucherPercentDiscountOf30);

        // Then
        List<Voucher> retrievedVoucherList = voucherRepository.findAll();

        assertThat(retrievedVoucherList).hasSize(2)
                .containsExactlyInAnyOrder(voucherFixedAmountOf500, voucherPercentDiscountOf30);
    }

    @Test
    @DisplayName("유저 이름에 해당하는 할당된 바우처를 받는다.")
    void GetAssignedVoucherListByUsername_Username_SameAssignVoucherList() {
        // Given
        voucherFixedAmountOf500 = new Voucher(voucherUuid1, fixedAmountDiscountStrategy);
        voucherRepository.save(voucherFixedAmountOf500);

        Wallet wallet = new Wallet(userTyler.userId(), voucherFixedAmountOf500.voucherId());
        walletRepository.save(wallet);

        // When
        List<Voucher> assignedVoucherList = voucherRepository.getAssignedVoucherListByUsername(usernameTyler);

        // Then
        assertThat(assignedVoucherList).isNotNull()
                .hasSize(1);
        assertThat(assignedVoucherList.get(0)).isEqualTo(voucherFixedAmountOf500);
    }

    @Test
    @DisplayName("할당된 바우처들의 리스트를 돌려 받는다.")
    void GetAssignedVoucherList_NoParam_SameAssignedVoucherList() {
        // Given
        voucherFixedAmountOf500 = new Voucher(voucherUuid1, fixedAmountDiscountStrategy);
        voucherRepository.save(voucherFixedAmountOf500);

        voucherPercentDiscountOf30 = new Voucher(voucherUuid2, percentDiscountStrategy);
        voucherRepository.save(voucherPercentDiscountOf30);

        Wallet walletFixedAmountOf500 = new Wallet(userTyler.userId(), voucherFixedAmountOf500.voucherId());
        walletRepository.save(walletFixedAmountOf500);

        Wallet walletPercentDiscountOf30 = new Wallet(userTyler.userId(), voucherPercentDiscountOf30.voucherId());
        walletRepository.save(walletPercentDiscountOf30);

        // When
        List<Voucher> assignedVoucherList = voucherRepository.getAssignedVoucherList();

        // Then
        assertThat(assignedVoucherList).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(voucherPercentDiscountOf30, voucherFixedAmountOf500);
    }

    @Test
    @DisplayName("할당되지 않은 바우처를 리스트를 받는다.")
    void GetNotAssignedVoucher_NoParam_SameNotAssignedVoucherList() {
        // Given
        voucherFixedAmountOf500 = new Voucher(voucherUuid1, fixedAmountDiscountStrategy);
        voucherRepository.save(voucherFixedAmountOf500);

        voucherPercentDiscountOf30 = new Voucher(voucherUuid2, percentDiscountStrategy);
        voucherRepository.save(voucherPercentDiscountOf30);

        // When
        List<Voucher> notAssignedVoucherList = voucherRepository.getNotAssignedVoucherList();

        // Then
        assertThat(notAssignedVoucherList).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(voucherFixedAmountOf500, voucherPercentDiscountOf30);
    }
}