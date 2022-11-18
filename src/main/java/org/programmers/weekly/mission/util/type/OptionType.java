package org.programmers.weekly.mission.util.type;

import java.util.Optional;

public enum OptionType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACK_LIST("blacklist"),
    WALLET("wallet"),
    CUSTOMER("customer"),
    VOUCHER("voucher"),
    DELETE("delete");

    private final String option;

    OptionType(String option) {
        this.option = option;
    }

    public static Optional<OptionType> checkType(String input) {
        for (OptionType optionType : OptionType.values()) {
            if (optionType.option.equalsIgnoreCase(input)) return Optional.of(optionType);
        }
        return Optional.empty();
    }
}
