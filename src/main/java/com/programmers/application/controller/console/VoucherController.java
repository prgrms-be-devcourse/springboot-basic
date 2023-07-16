package com.programmers.application.controller.console;

import com.programmers.application.controller.console.voucher.command.CommandExecution;
import com.programmers.application.controller.console.voucher.command.VoucherCommand;
import com.programmers.application.io.IO;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VoucherController implements Controller{
    private final IO io;
    private final CommandExecution commandExecution;

    public VoucherController(IO io, CommandExecution commandExecution) {
        this.io = io;
        this.commandExecution = commandExecution;
    }

    @Override
    public void process() throws IOException {
        printMenu();
        VoucherCommand command = VoucherCommand.valueOf(io.read().toUpperCase());
        commandExecution.executeVoucher(command);
    }

    private void printMenu() throws IOException {
        io.write("=== Voucher Program ===");
        io.write("Enter a exit to exit the program");
        io.write("Enter a create to create a new voucher");
        io.write("Enter a list to list all vouchers");
    }
}
