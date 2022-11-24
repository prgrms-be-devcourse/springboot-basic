package org.programmers.weekly.mission.util.type;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum OptionType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACK_LIST("blacklist");

    private final String option;

    OptionType(String option) {
        this.option = option;
    }

    public static OptionType checkType(String input) {
        return Stream.of(OptionType.values())
                .filter(optionType -> optionType.option.equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력값"));
    }
}
