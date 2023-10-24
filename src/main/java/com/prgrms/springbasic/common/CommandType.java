package com.prgrms.springbasic.common;

import java.util.EnumSet;
import java.util.List;

public enum CommandType {
    CREATE,
    LIST,
    BLACK_LIST,
    UPDATE,
    DELETE_ALL;

    public static CommandType find(String command) {
        return CommandType.valueOf(command);
    }

    public static List<String> allowedCommandTypes(MenuType menuType) {
        EnumSet<CommandType> allowedTypes = EnumSet.noneOf(CommandType.class);

        if (menuType == MenuType.VOUCHER) {
            allowedTypes.addAll(EnumSet.of(CREATE, LIST, UPDATE, DELETE_ALL));
        } else if (menuType == MenuType.CUSTOMER) {
            allowedTypes.addAll(EnumSet.of(CREATE, BLACK_LIST));
        }

        return allowedTypes.stream()
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }
}
