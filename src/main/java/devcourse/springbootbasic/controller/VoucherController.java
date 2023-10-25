package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherResponse;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
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

    public VoucherResponse createVoucher(VoucherCreateRequest voucherCreateRequest) {
        return new VoucherResponse(this.voucherService.create(voucherCreateRequest));
    }

    public List<VoucherFindResponse> findAllVouchers() {
        return this.voucherService.findAll();
    }

    public VoucherResponse updateDiscountValue(VoucherUpdateDiscountValueRequest voucherUpdateDiscountValueRequest) {
        return new VoucherResponse(this.voucherService.updateDiscountValue(voucherUpdateDiscountValueRequest));
    }

    public VoucherResponse deleteVoucher(UUID voucherId) {
        return new VoucherResponse(this.voucherService.delete(voucherId));
    }
}
