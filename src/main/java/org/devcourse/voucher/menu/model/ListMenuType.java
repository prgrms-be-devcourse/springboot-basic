package org.devcourse.voucher.menu.model;

import java.util.Arrays;

public enum ListMenuType {
    VOUCHER("1"),
    BLACKLIST("2"),
    CUSTOMER("3"),
    NONE("");

    private String option;

    ListMenuType(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public static ListMenuType discriminate(String input) {
        return Arrays.stream(values())
                .filter(type -> type.getOption().equals(input))
                .findFirst()
                .orElse(NONE);
    }
}
