package com.programmers.springbootbasic.domain.userVoucherWallet.presentation;

import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import com.programmers.springbootbasic.domain.userVoucherWallet.application.UserVoucherWalletService;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.CreateUserVoucherWalletRequest;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.UserOwnedVoucherResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;

@Controller
public class UserVoucherWalletController {

    private final UserVoucherWalletService userVoucherWalletService;

    public UserVoucherWalletController(UserVoucherWalletService userVoucherWalletService) {
        this.userVoucherWalletService = userVoucherWalletService;
    }

    public void createUserVoucher(CreateUserVoucherWalletRequest request) {
        userVoucherWalletService.create(request);
    }

    public void deleteUserVoucher(Long id) {
        userVoucherWalletService.deleteById(id);
    }

    public List<UserResponse> findUserByVoucherId(UUID voucherId) {
        return userVoucherWalletService.findUserByVoucherId(voucherId);
    }

    public List<UserOwnedVoucherResponse> findVoucherByUserNickname(String nickname) {
        return userVoucherWalletService.findVoucherByUserNickname(nickname);
    }
}
