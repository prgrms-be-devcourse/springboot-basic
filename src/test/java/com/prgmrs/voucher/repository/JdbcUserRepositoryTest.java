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
@Import({JdbcUserRepository.class, JdbcWalletRepository.class, JdbcVoucherRepository.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:VoucherMgmtDDL-test.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:VoucherMgmtCleanup-test.sql")
})
@DisplayName("JDBC 유저 레포지토리 레이어를 테스트한다.")
class JdbcUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    private UUID uuidTyler;
    private UUID uuidEmma;
    private Username usernameTyler;
    private Username usernameEmma;
    private Voucher voucherFixedAmountOf500;
    private Voucher voucherPercentDiscountOf30;

    @BeforeEach
    void setUp() {
        uuidTyler = UUID.randomUUID();
        usernameTyler = new Username("tyler");

        uuidEmma = UUID.randomUUID();
        usernameEmma = new Username("emma");

        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
        voucherFixedAmountOf500 = new Voucher(voucherUuid1, fixedAmountDiscountStrategy);
        voucherRepository.save(voucherFixedAmountOf500);

        UUID voucherUuid2 = UUID.randomUUID();
        Percent percent = new Percent(30);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        voucherPercentDiscountOf30 = new Voucher(voucherUuid2, percentDiscountStrategy);
        voucherRepository.save(voucherPercentDiscountOf30);
    }

    @Test
    @DisplayName("유저를 저장한다.")
    void Save_User_SameUser() {
        // Given
        User user = new User(uuidTyler, usernameTyler);

        // When
        userRepository.save(user);

        // Then
        User retrievedUser = userRepository.findByUsername(usernameTyler);

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.userId()).isEqualTo(user.userId());
        assertThat(retrievedUser.username().value()).isEqualTo(user.username().value());
    }

    @Test
    @DisplayName("모든 유저를 불러온다.")
    void FindAll_NoParam_SameList() {
        // Given
        User userTyler = new User(uuidTyler, usernameTyler);
        User userEmma = new User(uuidEmma, usernameEmma);
        userRepository.save(userTyler);
        userRepository.save(userEmma);

        // When
        List<User> retrievedUsers = userRepository.findAll();

        // Then
        assertThat(retrievedUsers).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(userEmma, userTyler);
    }

    @Test
    @DisplayName("이름에 해당하는 유저를 찾는다.")
    void FindByUsername_Username_SameUser() {
        // Given
        User userTyler = new User(uuidTyler, usernameTyler);
        userRepository.save(userTyler);

        // When
        User retrievedUser = userRepository.findByUsername(userTyler.username());

        // Then
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.userId()).isEqualTo(userTyler.userId());
        assertThat(retrievedUser.username().value()).isEqualTo(userTyler.username().value());
    }

    @Test
    @DisplayName("바우처를 할당받은 유저 리스트를 반환한다.")
    void GetUserListWithVoucherAssigned_NoParam_SameUserListWithVoucher() {
        // Given
        User userTyler = new User(uuidTyler, usernameTyler);
        userRepository.save(userTyler);

        User userEmma = new User(uuidEmma, usernameEmma);
        userRepository.save(userEmma);

        Wallet walletTyler = new Wallet(userTyler.userId(), voucherFixedAmountOf500.voucherId());
        walletRepository.save(walletTyler);

        Wallet walletEmma = new Wallet(userEmma.userId(), voucherPercentDiscountOf30.voucherId());
        walletRepository.save(walletEmma);

        // When
        List<User> userListWithVoucherAssigned = userRepository.getUserListWithVoucherAssigned();

        // Then
        assertThat(userListWithVoucherAssigned).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(userTyler, userEmma);
    }

    @Test
    @DisplayName("할당받은 바우처 아이디에 해당하는 유저를 받는다.")
    void GetUserByVoucherId_VoucherId_SameUser() {
        // Given
        User userTyler = new User(uuidTyler, usernameTyler);
        userRepository.save(userTyler);

        User userEmma = new User(uuidEmma, usernameEmma);
        userRepository.save(userEmma);

        Wallet walletTyler = new Wallet(userTyler.userId(), voucherFixedAmountOf500.voucherId());
        walletRepository.save(walletTyler);

        Wallet walletEmma = new Wallet(userEmma.userId(), voucherPercentDiscountOf30.voucherId());
        walletRepository.save(walletEmma);

        // When
        User userWithVoucher1 = userRepository.getUserByVoucherId(voucherFixedAmountOf500.voucherId());
        User userWithVoucher2 = userRepository.getUserByVoucherId(voucherPercentDiscountOf30.voucherId());

        // Then
        assertThat(userWithVoucher1)
                .isNotNull()
                .isEqualTo(userTyler);

        assertThat(userWithVoucher2)
                .isNotNull()
                .isEqualTo(userEmma);
    }
}