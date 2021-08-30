package org.prgrms.kdt.io.console;

import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.io.file.IO;

import java.io.IOException;
import java.util.Arrays;

public enum Command {
    EXIT("EXIT"), LIST("LIST"), CREATE("CREATE");

    private final String value;

    Command(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public static boolean isInvalidCommand(String cmd) {
        return Arrays.stream(Command.values())
                .filter(c -> c.getValue().equals(cmd))
                .findFirst()
                .isEmpty();
    }

    public static void doAction(VoucherService voucherService, IO io, Command command) throws IOException {
        switch (command) {
            case EXIT -> {
                CommandAction exitCommandAction = new ExitCommandAction(io);
                exitCommandAction.action();
            }
            case CREATE -> {
                CommandAction create = new CreateCommandAction(voucherService, io);
                create.action();
            }
            case LIST -> {
                CommandAction list = new ListCommandAction(voucherService, io);
                list.action();
            }
            default -> new IllegalArgumentException("Wrong Command");
        }
    }
}
