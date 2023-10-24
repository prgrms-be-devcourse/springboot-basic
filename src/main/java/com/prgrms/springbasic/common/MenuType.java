package com.prgrms.springbasic.common;

import java.util.Arrays;
import java.util.List;

public enum MenuType {
    EXIT,
    VOUCHER,
    CUSTOMER
    ;

    public static MenuType find(String menu) {
        return MenuType.valueOf(menu);
    }

    public static List<String> allowedMenuTypes() {
        return Arrays.stream(MenuType.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }
}
