package com.programmers.voucher.controller;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.request.VoucherCreateRequest;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public UUID createVoucher(VoucherCreateRequest voucherCreateRequest) {
        return voucherService.createVoucher(voucherCreateRequest);
    }

    public List<Voucher> findVouchers() {
        return voucherService.findVouchers();
    }
}
