package com.programmers.voucher;

import java.util.Arrays;

public enum Menu {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String menu;

    Menu(String menu) {
        this.menu = menu;
    }

    public static Menu getMenu(String orderedMenu) {
        return Arrays.stream(Menu.values())
                .filter(o -> o.menu.equals(orderedMenu))
                .findFirst()
                .get();
    }

}
