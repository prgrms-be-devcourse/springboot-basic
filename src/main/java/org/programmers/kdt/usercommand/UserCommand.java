package org.programmers.kdt.usercommand;

import java.text.MessageFormat;
import java.util.Arrays;

public enum UserCommand {
    CREATE("create"), LIST("list"), EXIT("exit");

    private final String command;

    UserCommand(String command) {
        this.command = command;
    }

    public static UserCommand of(String command) {
        return Arrays.stream(values())
                .filter(iter -> iter.getCommand().equals(command.toLowerCase()))
                .findAny()
                .orElseThrow(
                        () -> new RuntimeException(MessageFormat.format("Invalid Command : {0}", command))
                );
    }

    public String getCommand() {
        return command;
    }
}
