package com.waterfogsw.voucher.console;

import static com.waterfogsw.voucher.console.Messages.INVALID_COMMAND;

public enum Command {
    CREATE,
    LIST,
    BLACKLIST,
    EXIT;

    public static Command from(String command) {
        try {
            return Command.valueOf(command.toUpperCase());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(INVALID_COMMAND);
        }
    }
}
