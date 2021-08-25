package org.programmers.kdt.command;

import java.text.MessageFormat;
import java.util.Arrays;

public enum Command {
    CREATE("create"), LIST("list"), EXIT("exit");

    private final String command;

    Command(String command) {
        this.command = command;
    }


    public static Command of(String command) {
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
