package org.programmers.springorder.utils;

import org.programmers.springorder.constant.ErrorMessage;

import java.util.Arrays;

public enum VoucherMenuType {
    CREATE("1"),
    LIST("2"),
    UPDATE("3"),
    DELETE("4"),
    BACK("5");

    private final String menuNum;

    VoucherMenuType(String menuNum) {
        this.menuNum = menuNum;
    }

    private boolean isEqual(String voucherMenu) {
        return this.menuNum.equals(voucherMenu);
    }

    public static VoucherMenuType selectVoucherMenu(String voucherMenu) {
        return Arrays.stream(VoucherMenuType.values())
                .filter(menu -> menu.isEqual(voucherMenu))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VALUE_MESSAGE));
    }

}
