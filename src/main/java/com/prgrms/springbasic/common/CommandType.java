package com.prgrms.springbasic.common;

import java.util.EnumSet;
import java.util.List;

public enum CommandType {
    CREATE,
    LIST,
    BLACK_LIST,
    UPDATE,
    DELETE,
    DELETE_ALL,
    FIND_VOUCHERS,
    FIND_CUSTOMERS;

    public static CommandType find(String command) {
        return CommandType.valueOf(command);
    }

    public static List<String> allowedCommandTypes(MenuType menuType) {
        EnumSet<CommandType> allowedTypes = EnumSet.noneOf(CommandType.class);

        switch (menuType) {
            case VOUCHER -> allowedTypes.addAll(EnumSet.of(CREATE, LIST, UPDATE, DELETE_ALL));
            case CUSTOMER -> allowedTypes.addAll(EnumSet.of(CREATE, BLACK_LIST));
            case WALLET -> allowedTypes.addAll(EnumSet.of(CREATE, FIND_VOUCHERS, FIND_CUSTOMERS, DELETE));
        }

        return allowedTypes.stream()
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }
}
