package org.voucherProject.voucherProject;

import java.util.Arrays;

public enum InputCommand {
    EXIT,
    CREATE,
    LIST;

    public static InputCommand is(String input) {
        return Arrays.stream(InputCommand.values())
                .filter(i -> i.equals(InputCommand.valueOf(input.toUpperCase())))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
