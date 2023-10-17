package org.programmers.springorder.utils;

import java.util.Arrays;
import java.util.InputMismatchException;

public enum MenuType {
    EXIT,
    CREATE,
    LIST;

    public static MenuType selectMenu(String menuName) {
        return Arrays.stream(MenuType.values())
                .filter(menu -> menu.name().equals(menuName))
                .findAny()
                .orElseThrow(() -> new InputMismatchException("유효하지 않은 값입니다. 다시 입력해주세요."));
    }
}
