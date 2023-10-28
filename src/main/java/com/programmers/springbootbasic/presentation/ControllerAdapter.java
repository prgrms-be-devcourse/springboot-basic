package com.programmers.springbootbasic.presentation;

import static com.programmers.springbootbasic.exception.ErrorCode.EXIT;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_BLACK_USER_LIST;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_USER_FOUND_BY_VOUCHER;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_USER_VOUCHER_DELETE;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_USER_VOUCHER_FOUND_MINE;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_USER_VOUCHER_REGISTER;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_VOUCHER_DELETE;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_VOUCHER_FOUND;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_VOUCHER_LIST;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_VOUCHER_REGISTER;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_VOUCHER_UPDATE;

import com.programmers.springbootbasic.domain.user.presentation.UserController;
import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.UserVoucherWalletController;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.CreateUserVoucherWalletRequest;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.UserOwnedVoucherResponse;
import com.programmers.springbootbasic.domain.voucher.presentation.VoucherController;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherResponse;
import com.programmers.springbootbasic.exception.exceptionClass.SystemException;
import com.programmers.springbootbasic.mediator.ConsoleResponse;
import com.programmers.springbootbasic.mediator.dto.UpdateVoucherMediatorRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ControllerAdapter {

    private final VoucherController voucherController;
    private final UserController userController;
    private final UserVoucherWalletController userVoucherWalletController;

    public ControllerAdapter(
        VoucherController voucherController, UserController userController,
        UserVoucherWalletController userVoucherWalletController
    ) {
        this.voucherController = voucherController;
        this.userController = userController;
        this.userVoucherWalletController = userVoucherWalletController;
    }

    public ConsoleResponse<Void> handleExit(Object... params) {
        throw new SystemException(EXIT);
    }

    public ConsoleResponse<Void> createVoucher(Object... params) {
        CreateVoucherRequest request = (CreateVoucherRequest) params[0];
        voucherController.createVoucher(request);
        return ConsoleResponse.createNoBodyResponse(SUCCESS_VOUCHER_REGISTER.getMessage());
    }

    public ConsoleResponse<List<VoucherResponse>> getAllVouchers(Object... params) {
        return ConsoleResponse.createWithBodyResponse(voucherController.getAllVouchers(),
            SUCCESS_VOUCHER_LIST.getMessage());
    }

    public ConsoleResponse<VoucherResponse> getVoucherById(Object... params) {
        UUID id = (UUID) params[0];
        return ConsoleResponse.createWithBodyResponse(
            voucherController.getVoucherById(id),
            SUCCESS_VOUCHER_FOUND.getMessage());
    }

    public ConsoleResponse<Void> deleteVoucherById(Object... params) {
        UUID id = (UUID) params[0];
        voucherController.deleteVoucherById(id);
        return ConsoleResponse.createNoBodyResponse(SUCCESS_VOUCHER_DELETE.getMessage());
    }

    public ConsoleResponse<Void> updateVoucher(Object... params) {
        UpdateVoucherMediatorRequest request = (UpdateVoucherMediatorRequest) params[0];

        voucherController.updateVoucher(request.getId(), request.toUpdateVoucherRequest());
        return ConsoleResponse.createNoBodyResponse(SUCCESS_VOUCHER_UPDATE.getMessage());
    }

    public ConsoleResponse<List<UserResponse>> getBlackList(Object... params) {
        return ConsoleResponse.createWithBodyResponse(userController.getBlackList(),
            SUCCESS_BLACK_USER_LIST.getMessage());
    }

    public ConsoleResponse<Void> createUserVoucher(Object... params) {
        CreateUserVoucherWalletRequest request = (CreateUserVoucherWalletRequest) params[0];
        userVoucherWalletController.createUserVoucher(request);
        return ConsoleResponse.createNoBodyResponse(SUCCESS_USER_VOUCHER_REGISTER.getMessage());
    }

    public ConsoleResponse<List<UserResponse>> findUserByVoucherId(Object... params) {
        UUID voucherId = (UUID) params[0];
        return ConsoleResponse.createWithBodyResponse(
            userVoucherWalletController.findUserByVoucherId(voucherId),
            SUCCESS_USER_FOUND_BY_VOUCHER.getMessage());
    }

    public ConsoleResponse<List<UserOwnedVoucherResponse>> findVoucherByUserNickname(
        Object... params
    ) {
        String nickname = (String) params[0];
        return ConsoleResponse.createWithBodyResponse(
            userVoucherWalletController.findVoucherByUserNickname(nickname),
            SUCCESS_USER_VOUCHER_FOUND_MINE.getMessage());
    }

    public ConsoleResponse<Void> deleteUserVoucherById(Object... params) {
        Long id = (Long) params[0];
        userVoucherWalletController.deleteUserVoucher(id);
        return ConsoleResponse.createNoBodyResponse(SUCCESS_USER_VOUCHER_DELETE.getMessage());
    }


}
