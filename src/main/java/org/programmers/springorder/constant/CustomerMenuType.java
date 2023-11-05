package org.programmers.springorder.constant;

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
