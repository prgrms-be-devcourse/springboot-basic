package com.programmers.springbootbasic.domain.userVoucherWallet.presentation;

import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import com.programmers.springbootbasic.domain.userVoucherWallet.application.UserVoucherWalletService;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.CreateUserVoucherWalletRequest;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.UserOwnedVoucherResponse;
import com.programmers.springbootbasic.util.ConsoleValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Validated
public class UserVoucherWalletController {

    private final UserVoucherWalletService userVoucherWalletService;
    private final ConsoleValidator consoleValidator;

    public UserVoucherWalletController(UserVoucherWalletService userVoucherWalletService,
        ConsoleValidator consoleValidator
    ) {
        this.userVoucherWalletService = userVoucherWalletService;
        this.consoleValidator = consoleValidator;
    }

    public void createUserVoucher(@Valid CreateUserVoucherWalletRequest request) {
        consoleValidator.validate(request);
        userVoucherWalletService.create(request);
    }

    public void deleteUserVoucher(@NotNull Long id) {
        userVoucherWalletService.deleteById(id);
    }

    public List<UserResponse> findUserByVoucherId(@NotNull UUID voucherId) {
        return userVoucherWalletService.findUserByVoucherId(voucherId);
    }

    public List<UserOwnedVoucherResponse> findVoucherByUserNickname(@NotEmpty String nickname) {
        return userVoucherWalletService.findVoucherByUserNickname(nickname);
    }
}
