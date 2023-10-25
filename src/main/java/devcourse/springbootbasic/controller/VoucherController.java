package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.dto.voucher.VoucherResponse;
import devcourse.springbootbasic.dto.voucher.VoucherUpdateDiscountValueRequest;
import devcourse.springbootbasic.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public List<VoucherFindResponse> findAllVouchers() {
        return this.voucherService.findAllVouchers();
    }

    public VoucherResponse createVoucher(VoucherCreateRequest voucherCreateRequest) {
        return new VoucherResponse(this.voucherService.createVoucher(voucherCreateRequest));
    }

    public VoucherResponse updateDiscountValue(VoucherUpdateDiscountValueRequest voucherUpdateDiscountValueRequest) {
        return new VoucherResponse(this.voucherService.updateDiscountValue(voucherUpdateDiscountValueRequest));
    }

    public VoucherResponse deleteVoucher(UUID voucherId) {
        return new VoucherResponse(this.voucherService.deleteVoucher(voucherId));
    }
}
