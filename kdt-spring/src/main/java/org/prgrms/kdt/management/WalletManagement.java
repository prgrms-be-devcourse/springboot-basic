package org.prgrms.kdt.management;

import org.prgrms.kdt.domain.command.Command;
import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.WalletService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WalletManagement implements Management{

    private final IO io;
    private final WalletService walletService;

    public WalletManagement(@Qualifier("consoleIo") IO io, WalletService walletService) {
        this.io = io;
        this.walletService = walletService;
    }

    public void run() throws IOException, IllegalArgumentException {
        printInitMenu();
        String cmd = io.readLine().trim().toUpperCase();

        Command.validateCommand(cmd);

        Command command = Command.valueOf(cmd);
        execute(command);
    }

    public void execute(Command command) throws IOException {
        Command.doWalletAction(walletService, io, command);
    }

    public void printInitMenu() throws IOException {
        io.writeLine("=== Voucher Program ===");
        io.writeLine("Type exit to exit the program");
        io.writeLine("Type create to assign voucher to customer");
        io.writeLine("Type list to list customer's vouchers");
        io.writeLine("Type remove to remove customer's vouchers");
    }
}
