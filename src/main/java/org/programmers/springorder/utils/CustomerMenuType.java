package org.programmers.springorder.utils;

import org.programmers.springorder.constant.ErrorMessage;

import java.util.Arrays;

public enum CustomerMenuType {
    CREATE("1"),
    BLACK("2"),
    BACK("3");

    private final String menuNum;

    CustomerMenuType(String menuNum) {
        this.menuNum = menuNum;
    }

    private boolean isEqual(String customerMenu) {
        return this.menuNum.equals(customerMenu);
    }

    public static CustomerMenuType selectCustomerMenu(String customerMenu) {
        return Arrays.stream(CustomerMenuType.values())
                .filter(menu -> menu.isEqual(customerMenu))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VALUE_MESSAGE));
    }

    public boolean isBack() {
        return this == BACK;
    }
}
