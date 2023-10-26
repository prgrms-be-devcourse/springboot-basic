package com.prgrms.vouchermanagement.infra.utils;

import java.util.Arrays;

public enum MenuType {

    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private String value;

    MenuType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MenuType getType(String value) {
        return Arrays.stream(MenuType.values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }
}
