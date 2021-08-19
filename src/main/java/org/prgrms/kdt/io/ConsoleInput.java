package org.prgrms.kdt.io;

import java.util.Scanner;

import static org.prgrms.kdt.exception.Message.WRONG_TYPE_MESSAGE;

public class ConsoleInput implements Input {
    private static final String TITLE_MESSAGE = "=== Voucher Program ===";
    private static final String EXIT_HELP_MESSAGE = "Type to exit to exit the program.";
    private static final String CREATE_HELP_MESSAGE = "Type to create to create a new voucher.";
    private static final String LIST_HELP_MESSAGE = "Type list to list all vouchers.";
    private static final String VOUCHER_TYPE_MESSAGE = "Choose voucher type number: 1. fixed amount(1000원), 2. percent discount(10%)";
    private static final String FIXED_TYPE = "FIXED_AMOUNT";
    private static final String PERCENT_TYPE = "PERCENT_DISCOUNT";
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
        try {
            int typeNumber = scanner.nextInt();
            switch (typeNumber) {
                case 1:
                    return FIXED_TYPE;
                case 2:
                    return PERCENT_TYPE;
                default:
                    System.out.println(WRONG_TYPE_MESSAGE);
                    return inputVoucherType();
            }
        } catch (IllegalArgumentException ie) {
            System.out.println(WRONG_TYPE_MESSAGE);
            return inputVoucherType();
        }
    }

    @Override
    public void newLine() {
        System.out.println();
    }
}
