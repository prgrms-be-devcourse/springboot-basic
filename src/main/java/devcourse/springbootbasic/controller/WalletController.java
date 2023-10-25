package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.dto.wallet.VoucherAssignRequest;
import devcourse.springbootbasic.dto.wallet.VoucherAssignResponse;
import devcourse.springbootbasic.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    public VoucherAssignResponse assignVoucherToCustomer(VoucherAssignRequest voucherAssignRequest) {
        return this.walletService.assignVoucherToCustomer(voucherAssignRequest);
    }

    public List<VoucherFindResponse> findVouchersByCustomerId(UUID customerId) {
        return this.walletService.findVouchersByCustomerId(customerId);
    }

    public VoucherAssignResponse unassignVoucherFromCustomer(UUID voucherId) {
        return this.walletService.unassignVoucherFromCustomer(voucherId);
    }

    public CustomerFindResponse findCustomerByVoucherId(UUID voucherId) {
        return this.walletService.findCustomerByVoucherId(voucherId);
    }
}
