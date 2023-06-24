package org.prgrms.application;

import java.util.Arrays;
import java.util.Optional;

public enum CommandType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String option;

    CommandType(String option) {
        this.option = option;
    }

    public static Optional<CommandType> findBySelection(String selection) {
        return Arrays.stream(values())
                .filter(s -> s.option.equals(selection))
                .findFirst();
    }
}
