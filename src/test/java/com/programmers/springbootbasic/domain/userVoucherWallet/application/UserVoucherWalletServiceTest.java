package com.programmers.springbootbasic.domain.userVoucherWallet.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.programmers.springbootbasic.common.TimeGenerator;
import com.programmers.springbootbasic.domain.TestTimeGenerator;
import com.programmers.springbootbasic.domain.user.domain.UserRepository;
import com.programmers.springbootbasic.domain.user.domain.entity.User;
import com.programmers.springbootbasic.domain.userVoucherWallet.domain.UserVoucherWalletRepository;
import com.programmers.springbootbasic.domain.userVoucherWallet.domain.entity.UserVoucherWallet;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.CreateUserVoucherWalletRequest;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.infrastructure.dto.UserVoucherWalletWithUser;
import com.programmers.springbootbasic.domain.voucher.infrastructure.dto.UserVoucherWalletWithVoucher;
import com.programmers.springbootbasic.exception.exceptionClass.UserException;
import com.programmers.springbootbasic.exception.exceptionClass.UserVoucherWalletException;
import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ActiveProfiles("test")
@DisplayName("UserVoucherWalletService 테스트")
class UserVoucherWalletServiceTest {

    @InjectMocks
    private UserVoucherWalletService userVoucherWalletService;

    @Mock
    private UserVoucherWalletRepository userVoucherWalletRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VoucherRepository voucherRepository;

    private TimeGenerator timeGenerator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.timeGenerator = new TestTimeGenerator();
        ReflectionTestUtils.setField(userVoucherWalletService, "timeGenerator", timeGenerator);
    }

    @DisplayName("존재 하는 유저가 존재 하는 쿠폰을 등록 할 수 있다.")
    @Test
    void success_create() {
        // given
        var existedUUID = UUID.randomUUID();
        var existedNickname = "nickname";
        var userId = 1L;
        var userVoucherWalletId = 1L;
        CreateUserVoucherWalletRequest request = new CreateUserVoucherWalletRequest(
            existedNickname, existedUUID
        );
        when(userRepository.findByNickname(existedNickname)).thenReturn(
            Optional.of(new User(userId, existedNickname, false)));
        when(voucherRepository.findById(existedUUID)).thenReturn(
            Optional.of(
                new Voucher(existedUUID, new FixedAmountVoucher(100), 100)
            )
        );
        when(userVoucherWalletRepository.save(any()))
            .thenReturn(
                new UserVoucherWallet(userVoucherWalletId, userId, existedUUID, timeGenerator.now())
            );

        // when
        var result = userVoucherWalletService.create(request);

        // then
        assertThat(result).isEqualTo(userVoucherWalletId);
    }

    @DisplayName("존재하지 않는 유저가 쿠폰을 등록 하려하면 예외가 발생한다.")
    @Test
    void fail_create_notExistedUser() {
        // given
        var existedUUID = UUID.randomUUID();
        var notExistedNickname = "notExistedNickname";
        CreateUserVoucherWalletRequest request = new CreateUserVoucherWalletRequest(
            notExistedNickname, existedUUID
        );
        when(userRepository.findByNickname(notExistedNickname)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> userVoucherWalletService.create(request))
            .isInstanceOf(UserException.class)
            .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }

    @DisplayName("존재하지 않는 쿠폰을 등록 하려 하면 예외가 발생한다.")
    @Test
    void success_create_notExistedVoucher() {
        // given
        var notExistedUUID = UUID.randomUUID();
        var existedNickname = "existedNickname";
        CreateUserVoucherWalletRequest request = new CreateUserVoucherWalletRequest(
            existedNickname, notExistedUUID
        );
        when(userRepository.findByNickname(existedNickname)).thenReturn(
            Optional.of(new User(1L, existedNickname, false)));
        when(voucherRepository.findById(notExistedUUID)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> userVoucherWalletService.create(request))
            .isInstanceOf(VoucherException.class)
            .hasMessageContaining("바우처를 찾을 수 없습니다.");
    }

    @DisplayName("존재하는 바우처 아이디로 바우처를 소유한 유저를 조회 할 수 있다.")
    @Test
    void success_findUserByVoucherId() {
        // given
        var existedUUID = UUID.randomUUID();
        when(voucherRepository.findById(existedUUID)).thenReturn(
            Optional.of(new Voucher(existedUUID, new FixedAmountVoucher(100), 100))
        );
        when(userVoucherWalletRepository.findUserByVoucherId(existedUUID)).thenReturn(
            List.of(
                new UserVoucherWalletWithUser(1L, new User(1L, "nickname1", false)),
                new UserVoucherWalletWithUser(2L, new User(2L, "nickname2", false))
            )
        );

        // when
        var result = userVoucherWalletService.findUserByVoucherId(existedUUID);

        // then
        assertThat(result).hasSize(2);
    }

    @DisplayName("바우처를 소유한 유저를 조회 할 때, 중복된 유저는 제거된다.")
    @Test
    void success_findUserByVoucherId_duplicatedUserVoucher() {
        // given
        var existedUUID = UUID.randomUUID();
        when(voucherRepository.findById(existedUUID)).thenReturn(
            Optional.of(new Voucher(existedUUID, new FixedAmountVoucher(100), 100))
        );
        when(userVoucherWalletRepository.findUserByVoucherId(existedUUID)).thenReturn(
            List.of(
                new UserVoucherWalletWithUser(1L, new User(1L, "nickname1", false)),
                new UserVoucherWalletWithUser(2L, new User(1L, "nickname1", false))
            )
        );

        // when
        var result = userVoucherWalletService.findUserByVoucherId(existedUUID);

        // then
        assertThat(result).hasSize(1);
    }

    @DisplayName("바우처를 소유한 유저를 조회 할때 존재하지 않는 바우처 아이디 조회하면 예외가 발생한다.")
    @Test
    void fail_findUserByVoucherId_notExistedVoucher() {
        // given
        var notExistedUUID = UUID.randomUUID();
        when(voucherRepository.findById(notExistedUUID)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> userVoucherWalletService.findUserByVoucherId(notExistedUUID))
            .isInstanceOf(VoucherException.class)
            .hasMessageContaining("바우처를 찾을 수 없습니다.");
    }

    @DisplayName("존재하는 유저의 닉네임으로 소유한 바우처를 조회 할 수 있다.")
    @Test
    void success_findVoucherByUserNickname() {
        // given
        var existedNickname = "nickname";
        var userId = 1L;
        when(userRepository.findByNickname(existedNickname)).thenReturn(
            Optional.of(new User(userId, existedNickname, false))
        );
        when(userVoucherWalletRepository.findVoucherByUserId(userId)).thenReturn(
            List.of(
                new UserVoucherWalletWithVoucher(1L, new Voucher(UUID.randomUUID(),
                    new FixedAmountVoucher(100), 100)),
                new UserVoucherWalletWithVoucher(2L, new Voucher(UUID.randomUUID(),
                    new FixedAmountVoucher(100), 100))
            )
        );

        // when
        var result = userVoucherWalletService.findVoucherByUserNickname(existedNickname);

        // then
        assertThat(result).hasSize(2);
    }

    @DisplayName("존재하지 않는 유저의 닉네임으로 소유한 바우처를 조회 하려하면 예외가 발생한다.")
    @Test
    void fail_findVoucherByUserNickname_notExistedUser() {
        // given
        var notExistedNickname = "notExistedNickname";
        when(userRepository.findByNickname(notExistedNickname)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(
            () -> userVoucherWalletService.findVoucherByUserNickname(notExistedNickname))
            .isInstanceOf(UserException.class)
            .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }

    @DisplayName("id로 소유한 바우처를 삭제 할 수 있다.")
    @Test
    void success_deleteById() {
        // given
        var existedId = 1L;
        when(userVoucherWalletRepository.findById(existedId)).thenReturn(
            Optional.of(
                new UserVoucherWallet(existedId, 1L, UUID.randomUUID(), timeGenerator.now()))
        );
        when(userVoucherWalletRepository.deleteById(existedId)).thenReturn(1);

        // when
        // then
        assertThatCode(() -> userVoucherWalletService.deleteById(existedId))
            .doesNotThrowAnyException();
    }

    @DisplayName("존재하지 않는 id로 소유한 바우처를 삭제 하려하면 예외가 발생한다.")
    @Test
    void fail_deleteById_notExistedId() {
        // given
        var notExistedId = 1L;
        when(userVoucherWalletRepository.findById(notExistedId)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> userVoucherWalletService.deleteById(notExistedId))
            .isInstanceOf(UserVoucherWalletException.class)
            .hasMessageContaining("사용자 바우처를 찾을 수 없습니다.");
    }

    @DisplayName("존재하는 id로 소유한 바우처를 삭제 할 때, 삭제된 바우처가 없으면 예외가 발생한다.")
    @Test
    void success_deleteById_notDeletedUserVoucher() {
        // given
        var existedId = 1L;
        when(userVoucherWalletRepository.findById(existedId)).thenReturn(
            Optional.of(
                new UserVoucherWallet(existedId, 1L, UUID.randomUUID(), timeGenerator.now()))
        );
        when(userVoucherWalletRepository.deleteById(existedId)).thenReturn(0);

        // when
        // then
        assertThatThrownBy(() -> userVoucherWalletService.deleteById(existedId))
            .isInstanceOf(UserVoucherWalletException.class)
            .hasMessageContaining("바우처 삭제에 실패하였습니다.");
    }
}
