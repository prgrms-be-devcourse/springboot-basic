package org.prgrms.voucherapp.global;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;

public enum Menu {
    EXIT("exit", "exit the program"),
    CREATE("create", "create a new voucher"),
    LIST("list", "list all vouchers");

    private final String command;
    private final String action;

    Menu(String command, String action){
        this.command = command;
        this.action = action;
    }

    @Override
    public String toString(){
        return MessageFormat.format("Type {0} to {1}", command, action);
    }

    public static Optional<Menu> getMenu(String command) {
        return Arrays.stream(values())
                .filter(m -> m.command.equals(command))
                .findFirst();
    }
}
