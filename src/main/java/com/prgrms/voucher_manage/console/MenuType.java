package com.prgrms.voucher_manage.console;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MenuType {
    CREATE("create"),
    LIST("list"),
    CUSTOMER("customer"),
    EXIT("exit");

    private final String label;

    public static MenuType matchMenuType(String menu) {
        return Arrays.stream(MenuType.values())
                .filter(menuType -> menuType.getLabel().equals(menu))
                .findFirst()
                .orElse(null);
    }
}
