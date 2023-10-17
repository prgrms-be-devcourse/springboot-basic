package com.programmers.vouchermanagement.controller;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.infra.io.ConsoleInput;
import com.programmers.vouchermanagement.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class VoucherShellController {
    private final VoucherService voucherService;

    @ShellMethod(key = "create")
    public void create() {
        ConsoleInput consoleInput = new ConsoleInput();

        VoucherType voucherType = VoucherType.select(consoleInput.readVoucherTypeId());
        Long amount = consoleInput.readAmount();

        voucherService.createVoucher(voucherType, amount);
    }

    @ShellMethod(key = "list")
    public void list() {
        List<Voucher> vouchers = voucherService.getVouchers();
        for (Voucher voucher : vouchers) {
            System.out.println(voucher.toString());
        }
    }
}
