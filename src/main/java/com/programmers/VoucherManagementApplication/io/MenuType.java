package com.programmers.VoucherManagementApplication.io;

import java.util.stream.Stream;

public enum MenuType {

    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    DEFAULT("default");

    private final String value;

    MenuType(String value) {
        this.value = value;
    }

    public static MenuType getMenuType(String input) {
        return Stream.of(MenuType.values())
                .filter(menuType -> menuType.value.equals(input))
                .findFirst()
                .orElse(DEFAULT);
    }
}
