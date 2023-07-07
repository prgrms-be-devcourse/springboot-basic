package com.programmers.application.controller;

import com.programmers.application.controller.voucher.command.Command;
import com.programmers.application.io.IO;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VoucherController implements Controller{
    private final IO io;

    public VoucherController(IO io) {
        this.io = io;
    }

    @Override
    public void process() throws IOException {
        printMenu();
        Command command = Command.valueOf(io.read().toUpperCase());
        command.executeVoucher();
    }

    private void printMenu() throws IOException {
        io.write("=== Voucher Program ===");
        io.write("Enter a exit to exit the program");
        io.write("Enter a create to create a new voucher");
        io.write("Enter a list to list all vouchers");
    }
}
