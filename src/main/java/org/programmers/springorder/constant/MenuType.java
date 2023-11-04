package org.programmers.springorder.constant;

import java.util.Arrays;

public enum MenuType {
    EXIT("1"),
    VOUCHER("2"),
    CUSTOMER("3"),
    WALLET("4");

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

    public boolean isExit() {
        return this == EXIT;
    }
}
