package org.prgrms.voucherapp.global;

import java.util.Arrays;
import java.util.Optional;

/*
* Command : 커맨드를 enum으로 관리합니다.
* */
public enum Command {
    EXIT("exit", "exit the program"),
    CREATE("create", "create a new voucher"),
    LIST("list", "list all vouchers");

    private final String command;
    private final String action;

    Command(String command, String action){
        this.command = command;
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public String getCommand() {
        return command;
    }

    public static Optional<Command> getMenu(String command) {
        return Arrays.stream(values())
                .filter(c -> c.command.equals(command))
                .findFirst();
    }
}
