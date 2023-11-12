package com.prgrms.springbasic.console;

import java.util.Arrays;
import java.util.List;

public enum MenuType {
    EXIT,
    VOUCHER,
    CUSTOMER,
    WALLET
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
