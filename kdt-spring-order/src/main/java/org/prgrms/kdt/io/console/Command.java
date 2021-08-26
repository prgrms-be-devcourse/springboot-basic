package org.prgrms.kdt.io.console;

import java.util.Arrays;

public enum Command {
    EXIT("EXIT"), CONTINUE("CONTINUE"), LIST("LIST"), CREATE("CREATE");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isInvalidCommand(String cmd) {
        return Arrays.stream(Command.values())
                .filter(c -> c != CONTINUE)
                .filter(c -> c.getValue().equals(cmd))
                .findFirst()
                .isEmpty();
    }
}
