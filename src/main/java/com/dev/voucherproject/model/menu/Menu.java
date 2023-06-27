package com.dev.voucherproject.model.menu;


import java.util.Arrays;
import java.util.List;

public enum Menu {
    EXIT("exit"), CREATE("create"), LIST("list"), BLACKLIST("blacklist");

    private final String menuName;

    Menu(String menuName) {
        this.menuName = menuName;
    }

    public static Menu convertInputToMenu(final String input) {
        return Arrays.stream(Menu.values())
                .filter(m -> m.isExistMenu(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("입력 형식이 올바르지 않습니다."));
    }

    private boolean isExistMenu(final String input) {
        return this.menuName.equals(input);
    }

    public String getMenuName() {
        return menuName;
    }

    public static List<String> getMenuNames() {
        return Arrays.stream(Menu.values())
                .map(m -> m.menuName)
                .toList();
    }
}
