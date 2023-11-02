package com.programmers.springbootbasic.domain.userVoucherWallet.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.programmers.springbootbasic.common.TimeGenerator;
import com.programmers.springbootbasic.domain.user.domain.UserRepository;
import com.programmers.springbootbasic.domain.user.domain.entity.User;
import com.programmers.springbootbasic.domain.TestTimeGenerator;
import com.programmers.springbootbasic.domain.userVoucherWallet.domain.UserVoucherWalletRepository;
import com.programmers.springbootbasic.domain.userVoucherWallet.domain.entity.UserVoucherWallet;
import com.programmers.springbootbasic.domain.voucher.domain.ProdVoucherIdGenerator;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherIdGenerator;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayName("JdbcUserVoucherWalletRepository 테스트")
class JdbcUserVoucherWalletRepositoryTest {

    @Autowired
    private UserVoucherWalletRepository jdbcUserVoucherWalletRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    private final TimeGenerator timeGenerator = new TestTimeGenerator();
    private final VoucherIdGenerator idGenerator = new ProdVoucherIdGenerator();

    @DisplayName("Id 값이 null인 UserVoucherWallet가 Id 값이 할당 되어 저장 된다.")
    @Test
    void success_save() {
        // given
        var userVoucherWallet = createUserVoucherWallet();

        // when
        var result = jdbcUserVoucherWalletRepository.save(userVoucherWallet);

        // then
        assertThat(jdbcUserVoucherWalletRepository.findAll()).hasSize(1);
        assertNotNull(result.getId());
        assertThat(result).extracting("userId", "voucherId", "createdAt")
            .containsExactly(userVoucherWallet.getUserId(), userVoucherWallet.getVoucherId(),
                userVoucherWallet.getCreatedAt());
    }

    @DisplayName("Id 값이 null이 아닌 UserVoucherWallet를 저장해도 Id 값이 DB에 의해 생성 된다.")
    @Test
    void success_save_userVoucherWalletHasId() {
        // given
        var previousId = -1L;
        var user = saveUser("user1");
        var voucher = saveVoucher(idGenerator.generate());
        var userVoucherWallet = new UserVoucherWallet(previousId, user.getId(), voucher.getId(),
            timeGenerator.now());

        // when
        var result = jdbcUserVoucherWalletRepository.save(userVoucherWallet);

        // then
        assertThat(jdbcUserVoucherWalletRepository.findAll()).hasSize(1);
        assertNotNull(result.getId());
        assertNotEquals(result.getId(), previousId);
        assertThat(result).extracting("userId", "voucherId", "createdAt")
            .containsExactly(userVoucherWallet.getUserId(), userVoucherWallet.getVoucherId(),
                userVoucherWallet.getCreatedAt());
    }

    @DisplayName("존재하지 않는 User Id 값으로 UserVoucherWallet를 저장하면 예외가 발생한다.")
    @Test
    void fail_save_userIdNotExist() {
        // given
        var notExistUserId = 1L;
        var voucher = saveVoucher(idGenerator.generate());
        var userVoucherWallet = new UserVoucherWallet(null, notExistUserId, voucher.getId(),
            timeGenerator.now());

        // when & then
        assertThatThrownBy(() -> jdbcUserVoucherWalletRepository.save(userVoucherWallet))
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("존재하지 않는 Voucher Id 값으로 UserVoucherWallet를 저장하면 예외가 발생한다.")
    @Test
    void fail_save_voucherIdNotExist() {
        // given
        var user = saveUser("user1");
        var notExistVoucherId = idGenerator.generate();
        var userVoucherWallet = new UserVoucherWallet(null, user.getId(), notExistVoucherId,
            timeGenerator.now());

        // when & then
        assertThatThrownBy(() -> jdbcUserVoucherWalletRepository.save(userVoucherWallet))
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("존재하는 UserVoucherWallet Id 값으로 UserVoucherWallet를 찾을 수 있다.")
    @Test
    void success_findById() {
        // given
        var userVoucherWallet = createUserVoucherWallet();
        var savedUserVoucherWallet = jdbcUserVoucherWalletRepository.save(userVoucherWallet);

        // when
        var result = jdbcUserVoucherWalletRepository.findById(savedUserVoucherWallet.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).extracting("userId", "voucherId", "createdAt")
            .containsExactly(userVoucherWallet.getUserId(), userVoucherWallet.getVoucherId(),
                userVoucherWallet.getCreatedAt());
    }

    @DisplayName("모든 UserVoucherWallet를 찾을 수 있다.")
    @Test
    void success_findAll() {
        // given
        var userVoucherWallet1 = createUserVoucherWallet("user1", idGenerator.generate());
        var userVoucherWallet2 = createUserVoucherWallet("user2", idGenerator.generate());
        var userVoucherWallet3 = createUserVoucherWallet("user3", idGenerator.generate());
        jdbcUserVoucherWalletRepository.save(userVoucherWallet1);
        jdbcUserVoucherWalletRepository.save(userVoucherWallet2);
        jdbcUserVoucherWalletRepository.save(userVoucherWallet3);

        // when
        var result = jdbcUserVoucherWalletRepository.findAll();

        // then
        assertThat(result).hasSize(3);
        assertThat(result).extracting("userId", "voucherId", "createdAt")
            .containsExactlyInAnyOrder(
                tuple(userVoucherWallet1.getUserId(), userVoucherWallet1.getVoucherId(),
                    userVoucherWallet1.getCreatedAt()),
                tuple(userVoucherWallet2.getUserId(), userVoucherWallet2.getVoucherId(),
                    userVoucherWallet2.getCreatedAt()),
                tuple(userVoucherWallet3.getUserId(), userVoucherWallet3.getVoucherId(),
                    userVoucherWallet3.getCreatedAt())
            );
    }

    @DisplayName("존재하는 UserVoucherWallet Id 값으로 UserVoucherWallet를 삭제할 수 있다.")
    @Test
    void success_deleteById() {
        // given
        var userVoucherWallet = createUserVoucherWallet();
        var savedUserVoucherWallet = jdbcUserVoucherWalletRepository.save(userVoucherWallet);

        // when
        var result = jdbcUserVoucherWalletRepository.deleteById(savedUserVoucherWallet.getId());

        // then
        assertThat(result).isEqualTo(1);
        assertThat(jdbcUserVoucherWalletRepository.findAll()).isEmpty();
    }

    @DisplayName("존재하지 않는 UserVoucherWallet Id 값으로 UserVoucherWallet를 삭제하면 0을 반환 한다.")
    @Test
    void fail_deleteById_notFound() {
        // given
        var userVoucherWallet = createUserVoucherWallet();
        var savedUserVoucherWallet = jdbcUserVoucherWalletRepository.save(userVoucherWallet);
        Long notExistId = createNotExistedId(List.of(savedUserVoucherWallet.getId()));

        // when
        var result = jdbcUserVoucherWalletRepository.deleteById(notExistId);

        // then
        assertThat(result).isEqualTo(0);
        assertThat(jdbcUserVoucherWalletRepository.findAll()).hasSize(1);
    }

    @DisplayName("존재하는 User와 Voucher id 값으로 UserVoucherWallet를 수정할 수 있다.")
    @Test
    void success_update() {
        // given
        var userVoucherWallet = createUserVoucherWallet();
        var savedUserVoucherWallet = jdbcUserVoucherWalletRepository.save(userVoucherWallet);

        var newVoucherId = saveVoucher(idGenerator.generate()).getId();
        var newUserId = saveUser("user2").getId();
        var newUserVoucherWallet = new UserVoucherWallet(savedUserVoucherWallet.getId(),
            newUserId, newVoucherId, savedUserVoucherWallet.getCreatedAt());

        // when
        var result = jdbcUserVoucherWalletRepository.update(newUserVoucherWallet);

        // then
        assertThat(result).isEqualTo(1);
        assertThat(jdbcUserVoucherWalletRepository.findAll()).hasSize(1);
        assertThat(jdbcUserVoucherWalletRepository.findById(savedUserVoucherWallet.getId()))
            .isPresent()
            .get()
            .extracting("userId", "voucherId", "createdAt")
            .containsExactly(newUserVoucherWallet.getUserId(), newUserVoucherWallet.getVoucherId(),
                newUserVoucherWallet.getCreatedAt());
    }

    @DisplayName("존재하지 않는 User와 Voucher id 값으로 UserVoucherWallet를 수정하면 예외를 발생한다.")
    @Test
    void fail_update_notFound() {
        // given
        var userVoucherWallet = createUserVoucherWallet();
        var savedUserVoucherWallet = jdbcUserVoucherWalletRepository.save(userVoucherWallet);

        var newVoucherId = idGenerator.generate();
        var newUserId = createNotExistedId(List.of(savedUserVoucherWallet.getUserId()));
        var newUserVoucherWallet = new UserVoucherWallet(savedUserVoucherWallet.getId(), newUserId,
            newVoucherId,
            savedUserVoucherWallet.getCreatedAt());

        // when & then
        assertThatThrownBy(() -> jdbcUserVoucherWalletRepository.update(newUserVoucherWallet))
            .isInstanceOf(DataAccessException.class);
    }

    @DisplayName("존재하는 User Id 값으로 해당 유저가 가진 Voucher들을 찾을 수 있다.")
    @Test
    void success_findVoucherByUserId() {
        // given
        var user1 = saveUser("user1");
        var user2 = saveUser("user2");
        var voucher1 = saveVoucher(idGenerator.generate());
        var voucher2 = saveVoucher(idGenerator.generate());
        var voucher3 = saveVoucher(idGenerator.generate());
        // user1
        var userVoucherWallet1 = new UserVoucherWallet(null, user1.getId(), voucher1.getId(),
            timeGenerator.now());
        var userVoucherWallet2 = new UserVoucherWallet(null, user1.getId(), voucher2.getId(),
            timeGenerator.now());
        // user2
        var userVoucherWallet3 = new UserVoucherWallet(null, user2.getId(), voucher3.getId(),
            timeGenerator.now());
        var savedUserVoucherWallet1 = jdbcUserVoucherWalletRepository.save(userVoucherWallet1);
        var savedUserVoucherWallet2 = jdbcUserVoucherWalletRepository.save(userVoucherWallet2);
        var savedUserVoucherWallet3 = jdbcUserVoucherWalletRepository.save(userVoucherWallet3);

        // when
        var result = jdbcUserVoucherWalletRepository.findVoucherByUserId(user1.getId());

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id", "voucher")
            .containsExactlyInAnyOrder(
                tuple(savedUserVoucherWallet1.getId(), voucher1),
                tuple(savedUserVoucherWallet2.getId(), voucher2)
            );
    }

    @DisplayName("유저가 소유한 Voucher들을 조회 할때 같은 바우처를 여러개 가질 수 있다.")
    @Test
    void success_findVoucherByUserId_sameVoucher() {
        // given
        var user1 = saveUser("user1");
        var voucher1 = saveVoucher(idGenerator.generate());

        var userVoucherWallet1 = new UserVoucherWallet(null, user1.getId(), voucher1.getId(),
            timeGenerator.now());
        var userVoucherWallet2 = new UserVoucherWallet(null, user1.getId(), voucher1.getId(),
            timeGenerator.now());
        var userVoucherWallet3 = new UserVoucherWallet(null, user1.getId(), voucher1.getId(),
            timeGenerator.now());
        var savedUserVoucherWallet1 = jdbcUserVoucherWalletRepository.save(userVoucherWallet1);
        var savedUserVoucherWallet2 = jdbcUserVoucherWalletRepository.save(userVoucherWallet2);
        var savedUserVoucherWallet3 = jdbcUserVoucherWalletRepository.save(userVoucherWallet3);

        // when
        var result = jdbcUserVoucherWalletRepository.findVoucherByUserId(user1.getId());

        // then
        assertThat(result).hasSize(3);
        assertThat(result).extracting("id", "voucher")
            .containsExactlyInAnyOrder(
                tuple(savedUserVoucherWallet1.getId(), voucher1),
                tuple(savedUserVoucherWallet2.getId(), voucher1),
                tuple(savedUserVoucherWallet3.getId(), voucher1)
            );
    }

    @DisplayName("존재하는 Voucher Id 값으로 해당 Voucher를 가진 유저들을 찾을 수 있다.")
    @Test
    void success_findUserByVoucherId() {
        // given
        var user1 = saveUser("user1");
        var user2 = saveUser("user2");
        var voucher1 = saveVoucher(idGenerator.generate());
        var voucher2 = saveVoucher(idGenerator.generate());
        // user1
        var userVoucherWallet1 = new UserVoucherWallet(null, user1.getId(), voucher1.getId(),
            timeGenerator.now());
        var userVoucherWallet2 = new UserVoucherWallet(null, user1.getId(), voucher2.getId(),
            timeGenerator.now());
        // user2
        var userVoucherWallet3 = new UserVoucherWallet(null, user2.getId(), voucher1.getId(),
            timeGenerator.now());
        var savedUserVoucherWallet1 = jdbcUserVoucherWalletRepository.save(userVoucherWallet1);
        jdbcUserVoucherWalletRepository.save(userVoucherWallet2);
        var savedUserVoucherWallet3 = jdbcUserVoucherWalletRepository.save(userVoucherWallet3);

        // when
        var result = jdbcUserVoucherWalletRepository.findUserByVoucherId(voucher1.getId());

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id", "user")
            .containsExactlyInAnyOrder(
                tuple(savedUserVoucherWallet1.getId(), user1),
                tuple(savedUserVoucherWallet3.getId(), user2)
            );
    }

    @DisplayName("특정 Voucher를 가진 유저들을 조회 할때 같은 유저가 여러개 있을 수 있다.")
    @Test
    void success_findUserByVoucherId_sameUser() {
        // given
        var user1 = saveUser("user1");
        var voucher1 = saveVoucher(idGenerator.generate());

        var userVoucherWallet1 = new UserVoucherWallet(null, user1.getId(), voucher1.getId(),
            timeGenerator.now());
        var userVoucherWallet2 = new UserVoucherWallet(null, user1.getId(), voucher1.getId(),
            timeGenerator.now());
        var userVoucherWallet3 = new UserVoucherWallet(null, user1.getId(), voucher1.getId(),
            timeGenerator.now());
        var savedUserVoucherWallet1 = jdbcUserVoucherWalletRepository.save(userVoucherWallet1);
        var savedUserVoucherWallet2 = jdbcUserVoucherWalletRepository.save(userVoucherWallet2);
        var savedUserVoucherWallet3 = jdbcUserVoucherWalletRepository.save(userVoucherWallet3);

        // when
        var result = jdbcUserVoucherWalletRepository.findUserByVoucherId(voucher1.getId());

        // then
        assertThat(result).hasSize(3);
        assertThat(result).extracting("id", "user")
            .containsExactlyInAnyOrder(
                tuple(savedUserVoucherWallet1.getId(), user1),
                tuple(savedUserVoucherWallet2.getId(), user1),
                tuple(savedUserVoucherWallet3.getId(), user1)
            );
    }

    private User saveUser(String nickname) {
        return userRepository.save(new User(null, nickname, false));
    }

    private Voucher saveVoucher(UUID id) {
        return voucherRepository.save(
            new Voucher(id, new FixedAmountVoucher(100), 100)
        );
    }

    private UserVoucherWallet createUserVoucherWallet(String nickname, UUID id) {
        var user = saveUser(nickname);
        var voucher = saveVoucher(id);
        var userVoucherWallet = new UserVoucherWallet(null, user.getId(), voucher.getId(),
            timeGenerator.now());
        return userVoucherWallet;
    }

    private UserVoucherWallet createUserVoucherWallet() {
        return createUserVoucherWallet("user1", idGenerator.generate());
    }

    private static Long createNotExistedId(List<Long> existedIds) {
        Random random = new Random();
        Long notExistedId = null;
        do {
            var randomLong = random.nextLong();
            notExistedId = randomLong & Long.MAX_VALUE;
        } while (existedIds.contains(notExistedId));
        return notExistedId;
    }
}
