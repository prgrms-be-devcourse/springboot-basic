package com.programmers.springbootbasic.presentation;

import static com.programmers.springbootbasic.exception.ErrorCode.EXIT;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_BLACK_USER_LIST;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_VOUCHER_LIST;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_VOUCHER_REGISTER;

import com.programmers.springbootbasic.domain.user.presentation.UserController;
import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import com.programmers.springbootbasic.domain.voucher.presentation.VoucherController;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherResponse;
import com.programmers.springbootbasic.exception.exceptionClass.SystemException;
import com.programmers.springbootbasic.mediator.ConsoleResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ControllerAdapter {

    private final VoucherController voucherController;
    private final UserController userController;

    public ControllerAdapter(VoucherController voucherController, UserController userController) {
        this.voucherController = voucherController;
        this.userController = userController;
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
}
