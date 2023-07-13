package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.global.io.Console;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherConsoleController {
    private final Console console;
    private final VoucherService voucherService;

    public VoucherConsoleController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public UUID createVoucher(VoucherCreateRequest request) {
        return voucherService.createVoucher(request.getVoucherType(), request.getAmount());
    }

    public List<VoucherDto> findVouchers() {
        return voucherService.findVouchers();
    }

    public void deleteVoucher(UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }
}
