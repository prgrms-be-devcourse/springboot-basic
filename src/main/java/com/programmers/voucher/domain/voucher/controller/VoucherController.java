package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.global.io.Console;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final Console console;
    private final VoucherService voucherService;

    public VoucherController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void createVoucher() {
        VoucherCreateRequest request = console.inputVoucherCreateInfo();
        UUID voucherId = voucherService.createVoucher(request.getVoucherType(), request.getAmount());

        console.print("Created new voucher. VoucherID: " + voucherId.toString());
    }

    public void findVouchers() {
        List<Voucher> vouchers = voucherService.findVouchers();

        String vouchersForPrint = vouchers.stream()
                .map(Voucher::fullInfoString)
                .reduce("", (a, b) -> a + "\n" + b);

        console.print(vouchersForPrint);
    }
}
