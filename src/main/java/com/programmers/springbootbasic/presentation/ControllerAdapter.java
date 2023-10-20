package com.programmers.springbootbasic.presentation;

import static com.programmers.springbootbasic.util.Messages.SUCCESS_BLACK_USER_LIST;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_VOUCHER_LIST;
import static com.programmers.springbootbasic.util.Messages.SUCCESS_VOUCHER_REGISTER;

import com.programmers.springbootbasic.domain.user.presentation.UserController;
import com.programmers.springbootbasic.domain.voucher.presentation.VoucherController;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.mediator.ConsoleResponse;
import org.springframework.stereotype.Component;

@Component
public class ControllerAdapter {

    private final VoucherController voucherController;
    private final UserController userController;

    public ControllerAdapter(VoucherController voucherController, UserController userController) {
        this.voucherController = voucherController;
        this.userController = userController;
    }

    public ConsoleResponse createVoucher(Object... params) {
        CreateVoucherRequest request = (CreateVoucherRequest) params[0];
        voucherController.createVoucher(request);
        return new ConsoleResponse<>(SUCCESS_VOUCHER_REGISTER.getMessage());
    }

    public ConsoleResponse getAllVouchers(Object... params) {
        return new ConsoleResponse(voucherController.getAllVouchers(),
            SUCCESS_VOUCHER_LIST.getMessage()
        );
    }

    public ConsoleResponse getBlackList(Object... params) {
        return new ConsoleResponse(userController.getBlackList(),
            SUCCESS_BLACK_USER_LIST.getMessage());
    }
}
