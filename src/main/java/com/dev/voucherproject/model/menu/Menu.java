package com.dev.voucherproject.model.menu;


import java.text.MessageFormat;
import java.util.Arrays;

public enum Menu {
    EXIT("exit"), CREATE("create"), LIST("list"), BLACKLIST("blacklist");

    private final String menuName;

    Menu(String menuName) {
        this.menuName = menuName;
    }

    public static Menu convertInputToMenu(final String input) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.isExistMenu(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("{0} 입력에 해당하는 메뉴를 찾을 수 없습니다.", input)));
    }

    private boolean isExistMenu(final String input) {
        return this.menuName.equals(input);
    }

    public String getMenuName() {
        return menuName;
    }
}
