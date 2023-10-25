package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherResponse;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
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

    public VoucherResponse updateDiscountValue(UUID voucherId, long discountValue) {
        return new VoucherResponse(this.voucherService.updateDiscountValue(voucherId, discountValue));
    }

    public VoucherResponse deleteVoucher(UUID voucherId) {
        return new VoucherResponse(this.voucherService.delete(voucherId));
    }
}
