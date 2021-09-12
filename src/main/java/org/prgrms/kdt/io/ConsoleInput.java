package org.prgrms.kdt.io;

import java.util.Scanner;

import static org.prgrms.kdt.error.Message.WRONG_TYPE_MESSAGE;

public class ConsoleInput implements Input {
    private static final String TITLE_MESSAGE = "=== Voucher Program ===";
    private static final String EXIT_HELP_MESSAGE = "Type 'exit' to exit the program.";
    private static final String CREATE_HELP_MESSAGE = "Type 'create' to create to create a new voucher.";
    private static final String LIST_HELP_MESSAGE = "Type 'list' to list all vouchers.";
    private static final String ALLOCATE_HELP_MESSAGE = "Type 'allocate' to allocate a voucher to a customer.";
    private static final String CUSTOMER_ID_FOR_VOUCHER_MESSAGE = "Type 'customer id' to list customer's vouchers.";
    private static final String VOUCHER_ID_MESSAGE = "Type 'voucher id' to allocate.";
    private static final String CUSTOMER_LIST_MESSAGE = "Type 'customers' to list all customers.";
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
        System.out.println(ALLOCATE_HELP_MESSAGE);
        System.out.println(CUSTOMER_LIST_MESSAGE);
    }

    @Override
    public String inputCommand() {
        return scanner.next();
    }


    @Override
    public Long inputCustomerId() {
        System.out.println(CUSTOMER_ID_FOR_VOUCHER_MESSAGE);
        return scanner.nextLong();
    }

    @Override
    public Long inputVoucherId() {
        System.out.println(VOUCHER_ID_MESSAGE);
        return scanner.nextLong();
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
