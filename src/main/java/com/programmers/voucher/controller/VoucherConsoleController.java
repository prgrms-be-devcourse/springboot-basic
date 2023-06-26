package com.programmers.voucher.controller;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.request.VoucherCreateRequest;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherConsoleController implements VoucherController {
    private final VoucherService voucherService;

    public VoucherConsoleController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public UUID createVoucher(VoucherCreateRequest voucherCreateRequest) {
        return voucherService.createVoucher(voucherCreateRequest);
    }

    @Override
    public List<Voucher> findVouchers() {
        return voucherService.findVouchers();
    }
}
