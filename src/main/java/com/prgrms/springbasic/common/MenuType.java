package com.prgrms.springbasic.common;

import java.util.Arrays;
import java.util.List;

public enum MenuType {
    EXIT,
    CREATE,
    LIST,
    BLACK_LIST
    ;

    public static MenuType find(String menu) {
        return MenuType.valueOf(menu.toUpperCase());
    }

    public static List<String> allowedMenuTypes () {
        return Arrays.stream(MenuType.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }
}
