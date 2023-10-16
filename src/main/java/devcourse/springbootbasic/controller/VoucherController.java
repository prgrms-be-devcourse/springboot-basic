package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.dto.VoucherCreateRequest;
import devcourse.springbootbasic.dto.VoucherFindResponse;
import devcourse.springbootbasic.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public Voucher createVoucher(VoucherCreateRequest voucherCreateRequest) {
        return this.voucherService.create(voucherCreateRequest);
    }

    public List<VoucherFindResponse> findAllVouchers() {
        return this.voucherService.findAll();
    }
}
