package com.programmers.vouchermanagement.controller;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.infra.InputReader;
import com.programmers.vouchermanagement.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class VoucherShellController {
    private final VoucherService voucherService;

    @ShellMethod(key = "exit")
    public void exit() {
        System.exit(0);
    }

    @ShellMethod(key = "create")
    public void create() {
        InputReader inputReader = new InputReader();

        VoucherType voucherType = VoucherType.select(inputReader.readVoucherTypeId());
        Long amount = inputReader.readAmount();

        voucherService.create(voucherType, amount);
    }

    @ShellMethod(key = "list")
    public void list() {
        List<Voucher> vouchers = voucherService.getAll();
        for (Voucher voucher : vouchers) {
            System.out.println(voucher.toString());
        }
    }
}
