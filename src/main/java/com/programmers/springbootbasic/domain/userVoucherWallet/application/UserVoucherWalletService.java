package com.programmers.springbootbasic.domain.userVoucherWallet.application;

import static com.programmers.springbootbasic.exception.ErrorCode.FAIL_TO_DELETE_USER_VOUCHER;
import static com.programmers.springbootbasic.exception.ErrorCode.NOT_FOUND_USER;
import static com.programmers.springbootbasic.exception.ErrorCode.NOT_FOUND_USER_VOUCHER;
import static com.programmers.springbootbasic.exception.ErrorCode.NOT_FOUND_VOUCHER;

import com.programmers.springbootbasic.common.TimeGenerator;
import com.programmers.springbootbasic.domain.user.domain.UserRepository;
import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import com.programmers.springbootbasic.domain.userVoucherWallet.domain.UserVoucherWalletRepository;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.CreateUserVoucherWalletRequest;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.UserOwnedVoucherResponse;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.infrastructure.dto.UserVoucherWalletWithUser;
import com.programmers.springbootbasic.exception.exceptionClass.UserException;
import com.programmers.springbootbasic.exception.exceptionClass.UserVoucherWalletException;
import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserVoucherWalletService {

    private static final int AFFECTED_ROW_ONE = 1;
    private final UserVoucherWalletRepository userVoucherWalletRepository;
    private final UserRepository userRepository;
    private final VoucherRepository voucherRepository;
    private final TimeGenerator timeGenerator;

    public UserVoucherWalletService(
        UserVoucherWalletRepository userVoucherWalletRepository,
        UserRepository userRepository,
        VoucherRepository voucherRepository,
        TimeGenerator timeGenerator
    ) {
        this.userVoucherWalletRepository = userVoucherWalletRepository;
        this.userRepository = userRepository;
        this.voucherRepository = voucherRepository;
        this.timeGenerator = timeGenerator;
    }

    @Transactional
    public Long create(CreateUserVoucherWalletRequest request) {
        // 유효성 검사
        var user = userRepository.findByNickname(request.getNickname())
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));
        var voucher = voucherRepository.findById(request.getVoucherId())
            .orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));

        var nowTime = timeGenerator.now();
        var result = userVoucherWalletRepository.save(request.toEntity(user.getId(), nowTime));
        return result.getId();
    }

    public List<UserResponse> findUserByVoucherId(UUID voucherId) {
        voucherRepository.findById(voucherId)
            .orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));
        return userVoucherWalletRepository.findUserByVoucherId(voucherId).stream()
            .map(UserVoucherWalletWithUser::getUser)
            .distinct()
            .map(UserResponse::of)
            .toList();
    }

    public List<UserOwnedVoucherResponse> findVoucherByUserNickname(String nickname) {
        var user = userRepository.findByNickname(nickname)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        return userVoucherWalletRepository.findVoucherByUserId(user.getId()).stream()
            .map(dto -> UserOwnedVoucherResponse.of(dto.getId(), dto.getVoucher()))
            .toList();
    }

    @Transactional
    public void deleteById(Long id) {
        userVoucherWalletRepository.findById(id)
            .orElseThrow(() -> new UserVoucherWalletException(NOT_FOUND_USER_VOUCHER));

        int affectedRow = userVoucherWalletRepository.deleteById(id);
        if (affectedRow != AFFECTED_ROW_ONE) {
            throw new UserVoucherWalletException(FAIL_TO_DELETE_USER_VOUCHER);
        }
    }

}
