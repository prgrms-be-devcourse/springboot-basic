package com.programmers.springbootbasic.controller;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.infra.InputReader;
import com.programmers.springbootbasic.service.VoucherService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class VoucherController {
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
