package org.prgms.springbootbasic.cli;

import java.util.Arrays;

public enum Command {
    EXIT("exit", "Type exit to exit the program."),
    CREATE_VOUCHER("create_voucher", "Type create_voucher to create a new voucher."),
    CREATE_CUSTOMER("create_customer", "Type create_customer to create a new customer."),
    LIST_VOUCHER("list_voucher", "Type list_voucher to list all vouchers."),
    LIST_CUSTOMER("list_customer", "Type list_customer to list all vouchers."),
    MANAGE_VOUCHER("manage_voucher", "Type manage_voucher to associate customer with voucher"),

    FIND_CUSTOMER("find_customer", "Type find_customer to find customer by voucher id"),
    BLACKLIST("blacklist", "Type blacklist to list all blacklisted customer"),
    ;

    private final String command;

    private final String message;

    Command(String command, String message) {
        this.command = command;
        this.message = message;
    }

    public static Command findCommand(String input) {
        return Arrays.stream(Command.values())
                .filter(e -> e.command.equals(input.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("wrong command entered :\t {" + input + "}"));
    }

    public static String[] getMessages() {
        return Arrays.stream(Command.values()).map(e -> e.message).toArray(String[]::new);
    }
}
