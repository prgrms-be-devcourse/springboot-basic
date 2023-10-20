package com.prgrms.vouchermanagement.infra.utils;

import java.util.Arrays;

public enum MenuType {

    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private String menuValue;

    MenuType(String menuValue) {
        this.menuValue = menuValue;
    }

    public String getMenuValue() {
        return menuValue;
    }

    public static MenuType getMenuType(String menuValue) {
        return Arrays.stream(MenuType.values())
                .filter(type -> type.menuValue.equalsIgnoreCase(menuValue))
                .findFirst()
                .orElse(null);
    }
}
