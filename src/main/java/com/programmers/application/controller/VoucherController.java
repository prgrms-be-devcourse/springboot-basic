package com.programmers.application.controller;

import com.programmers.application.domain.command.Command;
import com.programmers.application.io.IO;
import com.programmers.application.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherController implements Controller{
    private final IO io;
    private final VoucherService voucherService;

    public VoucherController(IO io, VoucherService voucherService) {
        this.io = io;
        this.voucherService = voucherService;
    }

    @Override
    public void process() {
        printMenu();
        Command command = Command.valueOf(io.read().toUpperCase());
        command.executeVoucher(voucherService, command, io);
    }

    private void printMenu() {
        io.write("=== Voucher Program ===");
        io.write("Enter a exit to exit the program");
        io.write("Enter a create to create a new voucher");
        io.write("Enter a List to list all vouchers");
    }
}
