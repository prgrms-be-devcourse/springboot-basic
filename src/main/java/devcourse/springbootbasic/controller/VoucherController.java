package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.dto.voucher.VoucherResponse;
import devcourse.springbootbasic.dto.voucher.VoucherUpdateDiscountValueRequest;
import devcourse.springbootbasic.dto.wallet.VoucherAssignRequest;
import devcourse.springbootbasic.dto.wallet.VoucherAssignResponse;
import devcourse.springbootbasic.service.VoucherService;
import devcourse.springbootbasic.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;
    private final WalletService walletService;

    public List<VoucherFindResponse> findAllVouchers() {
        return this.voucherService.findAllVouchers();
    }

    public VoucherResponse createVoucher(VoucherCreateRequest voucherCreateRequest) {
        return new VoucherResponse(this.voucherService.createVoucher(voucherCreateRequest));
    }

    public VoucherResponse updateDiscountValue(UUID voucherId, VoucherUpdateDiscountValueRequest voucherUpdateDiscountValueRequest) {
        return new VoucherResponse(this.voucherService.updateDiscountValue(voucherId, voucherUpdateDiscountValueRequest));
    }

    public VoucherResponse deleteVoucher(UUID voucherId) {
        return new VoucherResponse(this.voucherService.deleteVoucher(voucherId));
    }

    public VoucherAssignResponse assignVoucherToCustomer(UUID voucherId, VoucherAssignRequest voucherAssignRequest) {
        return this.walletService.assignVoucherToCustomer(voucherId, voucherAssignRequest);
    }

    public VoucherAssignResponse unassignVoucherFromCustomer(UUID voucherId) {
        return this.walletService.unassignVoucherFromCustomer(voucherId);
    }

    public CustomerFindResponse findCustomerByVoucherId(UUID voucherId) {
        return this.walletService.findCustomerByVoucherId(voucherId);
    }
}
