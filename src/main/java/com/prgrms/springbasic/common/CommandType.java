package com.prgrms.springbasic.common;

import java.util.Arrays;
import java.util.List;

public enum CommandType {
    EXIT,
    CREATE,
    LIST,
    BLACK_LIST,
    CREATE_CUSTOMER,
    UPDATE_VOUCHER,
    DELETE_ALL
    ;

    public static CommandType find(String menu) {
        return CommandType.valueOf(menu);
    }

    public static List<String> allowedMenuTypes () {
        return Arrays.stream(CommandType.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }
}
