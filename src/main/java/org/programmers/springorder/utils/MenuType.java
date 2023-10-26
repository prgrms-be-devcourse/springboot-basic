package org.programmers.springorder.utils;

import org.programmers.springorder.consts.ErrorMessage;

import java.util.Arrays;

public enum MenuType {
    EXIT("1"),
    CREATE("2"),
    LIST("3"),
    BLACK("4");

    private final String menuNum;

    MenuType(String menuNum) {
        this.menuNum = menuNum;
    }

    private boolean isEqual(String menuNum) {
        return this.menuNum.equals(menuNum);
    }

    public static MenuType selectMenu(String menuNum) {
        return Arrays.stream(MenuType.values())
                .filter(menu -> menu.isEqual(menuNum))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VALUE_MESSAGE));
    }
}
