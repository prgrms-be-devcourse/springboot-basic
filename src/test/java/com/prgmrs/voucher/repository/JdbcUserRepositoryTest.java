package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.Wallet;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.Percent;
import org.junit.jupiter.api.DisplayName;
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
@Import({JdbcUserRepository.class, JdbcWalletRepository.class, JdbcVoucherRepository.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:VoucherMgmtDDL-test.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:VoucherMgmtCleanup-test.sql")
})
@DisplayName("JDBC 유저 레포지토리 레이어를 테스트한다.")
class JdbcUserRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    @DisplayName("유저를 저장한다.")
    void Save_User_SameUser() {
        // Given
        UUID uuid = UUID.randomUUID();
        String username = "tyler";
        User user = new User(uuid, username);

        // When
        userRepository.save(user);

        // Then
        User retrievedUser = userRepository.findByUsername(username);

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.userId()).isEqualTo(user.userId());
        assertThat(retrievedUser.username()).isEqualTo(user.username());

    }

    @Test
    @DisplayName("모든 유저를 불러온다.")
    void FindAll_NoParam_SameList() {
        // Given
        UUID uuid1 = UUID.randomUUID();
        String username1 = "tyler";
        User user1 = new User(uuid1, username1);
        userRepository.save(user1);

        UUID uuid2 = UUID.randomUUID();
        String username2 = "emma";
        User user2 = new User(uuid2, username2);
        userRepository.save(user2);


        // When
        List<User> retrievedUsers = userRepository.findAll();

        // Then
        assertThat(retrievedUsers).isNotNull()
            .hasSize(2);
    }

    @Test
    @DisplayName("이름에 해당하는 유저를 찾는다.")
    void FindByUsername_Username_SameUser() {
        // Given
        UUID uuid = UUID.randomUUID();
        String username = "tyler";
        User user = new User(uuid, username);
        userRepository.save(user);

        // When
        User retrievedUser = userRepository.findByUsername(username);

        // Then
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.userId()).isEqualTo(user.userId());
        assertThat(retrievedUser.username()).isEqualTo(user.username());
    }

    @Test
    @DisplayName("바우처를 할당받은 유저 리스트를 반환한다.")
    void GetUserListWithVoucherAssigned_NoParam_SameUserListWithVoucher() {
        // Given
        UUID userUuid1 = UUID.randomUUID();
        String username1 = "tyler";
        User user1 = new User(userUuid1, username1);
        userRepository.save(user1);

        UUID userUuid2 = UUID.randomUUID();
        String username2 = "emma";
        User user2 = new User(userUuid2, username2);
        userRepository.save(user2);

        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
        Voucher voucher1 = new Voucher(voucherUuid1, fixedAmountDiscountStrategy);
        voucherRepository.save(voucher1);

        UUID voucherUuid2 = UUID.randomUUID();
        Percent percent = new Percent(30);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        Voucher voucher2 = new Voucher(voucherUuid2, percentDiscountStrategy);
        voucherRepository.save(voucher2);

        Wallet wallet1 = new Wallet(userUuid1, voucherUuid1);
        walletRepository.save(wallet1);

        Wallet wallet2 = new Wallet(userUuid2, voucherUuid2);
        walletRepository.save(wallet2);

        // When
        List<User> userListWithVoucher = userRepository.getUserListWithVoucherAssigned();

        // Then
        assertThat(userListWithVoucher).isNotNull()
            .hasSize(2)
            .containsExactlyInAnyOrder(user1, user2);
    }

    @Test
    @DisplayName("할당받은 바우처 아이디에 해당하는 유저를 받는다.")
    void GetUserByVoucherId_VoucherId_SameUser() {
        // Given
        UUID userUuid1 = UUID.randomUUID();
        String username1 = "tyler";
        User user1 = new User(userUuid1, username1);
        userRepository.save(user1);

        UUID userUuid2 = UUID.randomUUID();
        String username2 = "emma";
        User user2 = new User(userUuid2, username2);
        userRepository.save(user2);

        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
        Voucher voucher1 = new Voucher(voucherUuid1, fixedAmountDiscountStrategy);
        voucherRepository.save(voucher1);

        UUID voucherUuid2 = UUID.randomUUID();
        Percent percent = new Percent(30);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        Voucher voucher2 = new Voucher(voucherUuid2, percentDiscountStrategy);
        voucherRepository.save(voucher2);

        Wallet wallet1 = new Wallet(userUuid1, voucherUuid1);
        walletRepository.save(wallet1);

        Wallet wallet2 = new Wallet(userUuid2, voucherUuid2);
        walletRepository.save(wallet2);

        // When
        User userWithVoucher1 = userRepository.getUserByVoucherId(voucher1);
        User userWithVoucher2 = userRepository.getUserByVoucherId(voucher2);


        // Then
        assertThat(userWithVoucher1)
            .isNotNull()
            .isEqualTo(user1);

        assertThat(userWithVoucher2)
            .isNotNull()
            .isEqualTo(user2);
    }
}