package com.dev.voucherproject.model.menu;

import java.util.Arrays;

public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String menuName;

    Menu(String menuName) {
        this.menuName = menuName;
    }

    private boolean isExistMenu(String input) {
        return this.menuName.equals(input);
    }

    public static Menu convertStringInputToMenu(String input) {
         return Arrays.stream(Menu.values())
                .filter(m -> m.isExistMenu(input))
                .findFirst().orElseThrow(() -> new RuntimeException("Invalid input value."));
    }
}
