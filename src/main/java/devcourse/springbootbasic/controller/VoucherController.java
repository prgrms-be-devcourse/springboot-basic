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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;
    private final WalletService walletService;

    @GetMapping
    public List<VoucherFindResponse> findAllVouchers() {
        return this.voucherService.findAllVouchers();
    }

    @PostMapping
    public VoucherResponse createVoucher(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        return new VoucherResponse(this.voucherService.createVoucher(voucherCreateRequest));
    }

    @PatchMapping("/{voucherId}/discount-value")
    public VoucherResponse updateDiscountValue(@PathVariable UUID voucherId, @RequestBody VoucherUpdateDiscountValueRequest voucherUpdateDiscountValueRequest) {
        return new VoucherResponse(this.voucherService.updateDiscountValue(voucherId, voucherUpdateDiscountValueRequest));
    }

    @DeleteMapping("/{voucherId}")
    public VoucherResponse deleteVoucher(@PathVariable UUID voucherId) {
        return new VoucherResponse(this.voucherService.deleteVoucher(voucherId));
    }

    @PatchMapping("/{voucherId}/assign")
    public VoucherAssignResponse assignVoucherToCustomer(@PathVariable UUID voucherId, @RequestBody VoucherAssignRequest voucherAssignRequest) {
        return this.walletService.assignVoucherToCustomer(voucherId, voucherAssignRequest);
    }

    @PatchMapping("/{voucherId}/unassign")
    public VoucherAssignResponse unassignVoucherFromCustomer(@PathVariable UUID voucherId) {
        return this.walletService.unassignVoucherFromCustomer(voucherId);
    }

    @GetMapping("/{voucherId}/customer")
    public CustomerFindResponse findCustomerByVoucherId(@PathVariable UUID voucherId) {
        return this.walletService.findCustomerByVoucherId(voucherId);
    }
}
