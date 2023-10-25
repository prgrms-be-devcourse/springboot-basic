package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.wallet.VoucherAssignRequest;
import devcourse.springbootbasic.dto.wallet.VoucherAssignResponse;
import devcourse.springbootbasic.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    public VoucherAssignResponse assignVoucherToCustomer(VoucherAssignRequest voucherAssignRequest) {
        return this.walletService.assignVoucherToCustomer(voucherAssignRequest);
    }
}
