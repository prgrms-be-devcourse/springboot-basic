package org.prgrms.kdt;

import org.prgrms.kdt.io.console.Command;
import org.prgrms.kdt.io.file.IO;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;

@Component
public class Console {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private final IO io;
    private final VoucherService voucherService;

    @Autowired
    public Console(@Qualifier("consoleIo") IO io, VoucherService voucherService) {
        this.io = io;
        this.voucherService = voucherService;
    }

    public void run() throws IOException {
        printInitMenu();
        String cmd = io.readLine().trim().toUpperCase();
        
        if (Command.isInvalidCommand(cmd)) {
            logger.warn(MessageFormat.format("Invalid command at {0}: cmd = {1}", this.getClass().getSimpleName(), cmd));
            io.writeLine("Invalid command, Try again");
            return;
        }
        Command command = Command.valueOf(cmd);
        execute(command);
    }

    private void execute(Command command) throws IOException {
        Command.doAction(voucherService, io, command);
    }

    private void printInitMenu() throws IOException {
        io.writeLine("=== Voucher Program ===");
        io.writeLine("Type exit to exit the program");
        io.writeLine("Type create to create a new voucher");
        io.writeLine("Type List to list all vouchers");
    }
}
