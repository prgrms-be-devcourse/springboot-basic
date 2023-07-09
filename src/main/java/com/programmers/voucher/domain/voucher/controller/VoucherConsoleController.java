package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.global.io.Console;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.global.util.ConsoleMessages.CREATED_NEW_VOUCHER;
import static com.programmers.voucher.global.util.ConsoleMessages.DELETED_VOUCHER;

@Controller
public class VoucherConsoleController {
    private final Console console;
    private final VoucherService voucherService;

    public VoucherConsoleController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void createVoucher() {
        VoucherCreateRequest request = console.inputVoucherCreateInfo();
        UUID voucherId = voucherService.createVoucher(request.getVoucherType(), request.getAmount());

        String consoleMessage = MessageFormat.format(CREATED_NEW_VOUCHER, voucherId.toString());
        console.print(consoleMessage);
    }

    public void findVouchers() {
        List<VoucherDto> voucherDtos = voucherService.findVouchers();

        console.printVouchers(voucherDtos);
    }

    public void deleteVoucher() {
        UUID voucherId = console.inputUUID();
        voucherService.deleteVoucher(voucherId);

        console.print(DELETED_VOUCHER);
    }
}
