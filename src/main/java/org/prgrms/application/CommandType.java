package org.prgrms.application;

import java.util.Arrays;
import java.util.Optional;

public enum CommandType {
    EXIT,
    CREATE,
    LIST;


    public static Optional<CommandType> findBySelection(String selection) {
        return Arrays.stream(values())
                .filter(s -> s.name().equals(selection.toUpperCase()))
                .findFirst();
    }
}
