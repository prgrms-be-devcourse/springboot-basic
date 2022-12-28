package org.programmers.weekly.mission.util.type;

import java.util.stream.Stream;

public enum OptionType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    WALLET("wallet"),
    CUSTOMER("customer"),
    VOUCHER("voucher"),
    DELETE("delete");

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
