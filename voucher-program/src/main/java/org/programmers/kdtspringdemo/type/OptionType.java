package org.programmers.kdtspringdemo.type;

import java.util.Optional;

public enum OptionType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String option;

    OptionType(String option) {
        this.option = option;
    }

    public static Optional<OptionType> checkType(String input) {
        for (OptionType optionType : OptionType.values()) {
            if (optionType.toString().equalsIgnoreCase(input)) return Optional.of(optionType);
        }
        return Optional.empty();
    }
}
