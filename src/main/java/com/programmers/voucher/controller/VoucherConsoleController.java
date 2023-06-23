package com.programmers.voucher.controller;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.request.VoucherCreateRequest;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoucherConsoleController implements VoucherController {
    private final VoucherService voucherService;

    public VoucherConsoleController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public UUID createVoucher(VoucherCreateRequest request) {
        return voucherService.createVoucher(request);
    }

    @Override
    public List<Voucher> findVouchers() {
        return voucherService.findVouchers();
    }


}
