package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public void createVoucher(VoucherDto.Request request) {
        voucherService.createVoucher(request);
    }

    public VoucherDto.Response getVouchers() {
        return voucherService.getVouchers();
    }
}
