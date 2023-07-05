package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.domain.voucher.util.VoucherMessages;
import com.programmers.voucher.global.io.Console;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.global.util.ConsoleMessages.CREATED_NEW_VOUCHER;
import static com.programmers.voucher.global.util.ConsoleMessages.DELETED_VOUCHER;

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

        String consoleMessage = VoucherMessages.addVoucherId(CREATED_NEW_VOUCHER, voucherId.toString());
        console.print(consoleMessage);
    }

    public void findVouchers() {
        List<Voucher> vouchers = voucherService.findVouchers();

        console.printVouchers(vouchers);
    }

    public void deleteVoucher() {
        UUID voucherId = console.inputUUID();

        voucherService.deleteVoucher(voucherId);

        console.print(DELETED_VOUCHER);
    }
}
