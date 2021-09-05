package org.prgrms.kdt.management;

import org.prgrms.kdt.domain.command.Command;
import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;

@Component
public class VoucherManagement implements Management{

    private final IO io;
    private final VoucherService voucherService;

    public VoucherManagement(@Qualifier("consoleIo") IO io, VoucherService voucherService) {
        this.io = io;
        this.voucherService = voucherService;
    }

    public void run() throws IOException, IllegalArgumentException {
        printInitMenu();
        String cmd = io.readLine().trim().toUpperCase();

        Command.validateCommand(cmd);

        Command command = Command.valueOf(cmd);
        execute(command);
    }

    public void execute(Command command) throws IOException {
        Command.doVoucherAction(voucherService, io, command);
    }

    public void printInitMenu() throws IOException {
        io.writeLine("=== Voucher Program ===");
        io.writeLine("Type exit to exit the program");
        io.writeLine("Type create to create a new voucher");
        io.writeLine("Type List to list all vouchers");
    }
}
