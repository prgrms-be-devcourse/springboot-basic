package org.programmers.springorder.utils;

import org.programmers.springorder.consts.ErrorMessage;

import java.util.Arrays;
import java.util.InputMismatchException;

public enum MenuType {
    EXIT("1"),
    CREATE("2"),
    LIST("3");

    private String menuNum;

    MenuType(String menuNum) {
        this.menuNum = menuNum;
    }

    public String getMenuNum() {
        return menuNum;
    }

    public static MenuType selectMenu(String menuNum) {
        return Arrays.stream(MenuType.values())
                .filter(menu -> menu.getMenuNum().equals(menuNum))
                .findAny()
                .orElseThrow(() -> new InputMismatchException(ErrorMessage.INVALID_VALUE_MESSAGE));
    }
}
