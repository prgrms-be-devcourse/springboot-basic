package com.programmers.springbootbasic.controller;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        VoucherType.printAllDescriptionsToConsole();

        System.out.print("Please select a voucher type > ");
        int voucherTypeId = Integer.parseInt(scanner.nextLine());
        VoucherType voucherType = VoucherType.select(voucherTypeId);

        System.out.print("Please enter the amount > ");
        Long amount = Long.parseLong(scanner.nextLine());

        service.create(voucherType, amount);

        scanner.close();
    }

    @ShellMethod(key = "list")
    public void list() {
        List<Voucher> vouchers = service.getAll();
        for (Voucher voucher : vouchers) {
            System.out.println(voucher.toString());
        }
    }
}
