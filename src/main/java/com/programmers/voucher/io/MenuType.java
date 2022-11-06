package com.programmers.voucher.io;

import java.util.stream.Stream;

public enum MenuType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String menuType;

    MenuType(String menuType) {
        this.menuType = menuType;
    }

    public Object getMenuType() {
        return menuType;
    }

    public static MenuType getMenuType(String inputMenu) {
        return Stream.of(MenuType.values())
                .filter(type -> type.getMenuType().equals(inputMenu))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
