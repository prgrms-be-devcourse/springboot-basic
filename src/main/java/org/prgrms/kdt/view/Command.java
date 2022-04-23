package org.prgrms.kdt.view;

import java.util.Arrays;

public enum Command {

    INIT("init", "==== Voucher Program ===="),
    EXIT("exit", "Type exit to exit the program."),
    CREATE("create", "Type create to create a new voucher."),
    LIST("list", "Type list to list all vouchers."),
    CUSTOMER("customer", "Type customer to list all BlackList.");

    private final String keyword;
    private final String message;

    Command(String keyword, String message) {
        this.keyword = keyword;
        this.message = message;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getMessage() {
        return message;
    }

    public static Command of(String s) {
        return Arrays.stream(Command.values())
            .filter(menu -> menu.getKeyword().equals(s))
            .findFirst()
            .orElse(Command.INIT);
    }

}
