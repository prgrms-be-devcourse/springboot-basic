package com.programmers.springbootbasic.controller;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.infra.InputReader;
import com.programmers.springbootbasic.service.VoucherService;
import com.programmers.springbootbasic.util.LoggingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class VoucherController {
    private final VoucherService voucherService;

    @ShellMethod(key = "exit")
    public void exit() {
        log.info(LoggingUtil.createRunLogMessage("exit"));
        System.exit(0);
    }

    @ShellMethod(key = "create")
    public void create() {
        log.info(LoggingUtil.createRunLogMessage("create"));

        InputReader inputReader = new InputReader();

        VoucherType voucherType = VoucherType.select(inputReader.readVoucherTypeId());
        Long amount = inputReader.readAmount();

        voucherService.create(voucherType, amount);
    }

    @ShellMethod(key = "list")
    public void list() {
        log.info(LoggingUtil.createRunLogMessage("list"));

        List<Voucher> vouchers = voucherService.getAll();
        for (Voucher voucher : vouchers) {
            System.out.println(voucher.toString());
        }
    }
}
