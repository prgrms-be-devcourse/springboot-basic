package com.prgrms.voucher_manage.console;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MenuType {
    CREATE("create"),
    LIST("list"),
    CUSTOMER("customer"),
    EXIT("exit");

    private final String label;

    MenuType(String label) {
        this.label = label;
    }

    public static MenuType matchMenuType(String menu) {
        return Arrays.stream(MenuType.values())
                .filter(menuType -> menuType.getLabel().equals(menu))
                .findFirst()
                .orElse(null);
    }
}
