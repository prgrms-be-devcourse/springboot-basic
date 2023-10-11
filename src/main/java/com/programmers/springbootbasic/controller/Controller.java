package com.programmers.springbootbasic.controller;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.infra.InputReader;
import com.programmers.springbootbasic.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class Controller {
    private final Service service;

    @Autowired
    public Controller(Service service) {
        this.service = service;
    }

    @ShellMethod(key = "exit")
    public void exit() {
        System.exit(0);
    }

    @ShellMethod(key = "create")
    public void create() {
        InputReader inputReader = new InputReader();

        VoucherType voucherType = VoucherType.select(inputReader.readVoucherTypeId());
        Long amount = inputReader.readAmount();

        service.create(voucherType, amount);

        inputReader.close();
    }

    @ShellMethod(key = "list")
    public void list() {
        List<Voucher> vouchers = service.getAll();
        for (Voucher voucher : vouchers) {
            System.out.println(voucher.toString());
        }
    }
}
