package org.prgrms.kdt.io;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private static final String TITLE_MESSAGE = "=== Voucher Program ===";
    private static final String EXIT_HELP_MESSAGE = "Type to exit to exit the program.";
    private static final String CREATE_HELP_MESSAGE = "Type to create to create a new voucher.";
    private static final String LIST_HELP_MESSAGE = "Type list to list all vouchers.";
    private static final String VOUCHER_TYPE_MESSAGE = "Choose voucher type: FIXED_AMOUNT(1000원), PERCENT_DISCOUNT(10%)";
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void inputHelp() {
        System.out.println(TITLE_MESSAGE);
        System.out.println(EXIT_HELP_MESSAGE);
        System.out.println(CREATE_HELP_MESSAGE);
        System.out.println(LIST_HELP_MESSAGE);
    }

    @Override
    public String inputCommand() {
        return scanner.nextLine();
    }

    @Override
    public String inputVoucherType() {
        System.out.println(VOUCHER_TYPE_MESSAGE);
        return scanner.nextLine();
    }

    @Override
    public void newLine() {
        System.out.println();
    }
}
