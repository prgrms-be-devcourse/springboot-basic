package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherCreateResponse;
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

    public VoucherCreateResponse createVoucher(VoucherCreateRequest voucherCreateRequest) {
        return new VoucherCreateResponse(this.voucherService.create(voucherCreateRequest));
    }

    public List<VoucherFindResponse> findAllVouchers() {
        return this.voucherService.findAll();
    }

    public VoucherCreateResponse updateDiscountValue(UUID voucherId, long discountValue) {
        return new VoucherCreateResponse(this.voucherService.updateDiscountValue(voucherId, discountValue));
    }

    public VoucherCreateResponse deleteVoucher(UUID voucherId) {
        return new VoucherCreateResponse(this.voucherService.delete(voucherId));
    }
}
