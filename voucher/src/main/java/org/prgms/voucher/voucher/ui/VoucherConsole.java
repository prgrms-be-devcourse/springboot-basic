package org.prgms.voucher.voucher.ui;

import org.prgms.voucher.ui.Console;

public class VoucherConsole implements Console {
    private static final String PROGRAM_NAME = "=== Voucher Program ===";
    private static final String EXIT_COMMANDS = "Type exit to exit the program.";
    private static final String CREATE_COMMANDS = "Type create to create a new voucher.";
    private static final String LIST_COMMANDS = "Type list to list all vouchers.";

    @Override
    public void printSupportedCommands() {
        String st = "\n" +
                PROGRAM_NAME + "\n" +
                EXIT_COMMANDS + "\n" +
                CREATE_COMMANDS + "\n" +
                LIST_COMMANDS + "\n";

        System.out.println(st);
    }
}
